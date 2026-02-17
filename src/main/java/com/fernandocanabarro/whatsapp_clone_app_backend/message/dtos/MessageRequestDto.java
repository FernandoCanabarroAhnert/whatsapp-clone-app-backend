package com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType type;
    private String chatId;
}
