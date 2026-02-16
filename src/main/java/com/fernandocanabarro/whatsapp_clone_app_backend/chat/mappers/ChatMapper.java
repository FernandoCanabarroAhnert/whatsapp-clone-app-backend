package com.fernandocanabarro.whatsapp_clone_app_backend.chat.mappers;

import org.springframework.stereotype.Service;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.dtos.ChatResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities.Chat;

@Service
public class ChatMapper {

    public ChatResponseDto toChatResponseDto(Chat chat, String senderId) {
        return ChatResponseDto.builder()
            .id(chat.getId())
            .name(chat.getChatName(senderId))
            .unreadCount(chat.getUnreadMessages(senderId))
            .lastMessage(chat.getLastMessage())
            .isReceiverOnline(chat.getReceiver().isUserOnline())
            .senderId(chat.getSender().getId())
            .receiverId(chat.getReceiver().getId())
            .build();
    }

}
