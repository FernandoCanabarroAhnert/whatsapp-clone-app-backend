package com.fernandocanabarro.whatsapp_clone_app_backend.chat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.dtos.ChatResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities.Chat;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.mappers.ChatMapper;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.repositories.ChatRepository;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.entities.User;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponseDto> getChatsByReceiverId(Authentication currentUser) {
        String userId = currentUser.getName();
        return this.chatRepository.findChatsByUserId(userId)
            .stream().map(chat -> this.chatMapper.toChatResponseDto(chat, userId)).toList();
    }

    @Transactional
    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = this.chatRepository.findChatBySenderIdAndReceiverId(senderId, receiverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }
        User sender = this.userRepository.findUserByPublicId(senderId)
            .orElseThrow(() -> new ResourceNotFoundException("Sender with id " + senderId + " not found"));
        User receiver = this.userRepository.findUserByPublicId(receiverId)
            .orElseThrow(() -> new ResourceNotFoundException("Receiver with id " + senderId + " not found"));
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        return this.chatRepository.save(chat).getId();
    }

}
