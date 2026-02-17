package com.fernandocanabarro.whatsapp_clone_app_backend.message.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities.Chat;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.repositories.ChatRepository;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos.MessageRequestDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos.MessageResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.entities.Message;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageState;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.mappers.MessageMapper;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.repositories.MessageRepository;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.services.BlobStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final BlobStorageService blobStorageService;

    @Transactional
    public void saveMessage(MessageRequestDto request) {
        Chat chat = this.chatRepository.findById(request.getChatId())
            .orElseThrow(() -> new ResourceNotFoundException("Chat with id " + request.getChatId() + " not found"));
        Message message = new Message();
        message.setChat(chat);
        message.setContent(request.getContent());
        message.setType(request.getType());
        message.setSenderId(request.getSenderId());
        message.setReceiverId(request.getReceiverId());
        message.setState(MessageState.SENT);
        this.messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageResponseDto> getChatMessages(String chatId) {
        return this.messageRepository.findMessagesByChatId(chatId).stream()
            .map(this.messageMapper::toMessageResponseDto)
            .toList();
    }

    @Transactional
    public void setMessagesToSeenByChat(String chatId, Authentication authentication) {
        Chat chat = this.chatRepository.findById(chatId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat with id " + chatId + " not found"));
        String receiverId = this.getReceiverId(chat, authentication);
        this.messageRepository.setMessagesToSeenByChat(MessageState.SEEN.toString(), chatId);
    }

    @Transactional
    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = this.chatRepository.findById(chatId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat with id " + chatId + " not found"));
        String senderId = this.getSenderId(chat, authentication);
        String receiverId = this.getReceiverId(chat, authentication);
        String blobName = chatId + "/" + senderId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        this.blobStorageService.uploadBlob(blobName, file);
        Message message = new Message();
        message.setChat(chat);
        message.setType(MessageType.IMAGE);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setState(MessageState.SENT);
        message.setBlobName(blobName);
        this.messageRepository.save(message);
    }

    private String getReceiverId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getReceiver().getId();
        }
        return chat.getSender().getId();
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getReceiver().getId();
    }

}
