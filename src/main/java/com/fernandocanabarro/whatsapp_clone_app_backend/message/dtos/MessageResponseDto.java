package com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos;


import java.time.LocalDateTime;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageState;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private Long id;
    private String content;
    private MessageType type;
    private MessageState state;
    private String senderId;
    private String receiverId;
    private LocalDateTime createdDate;
    private byte[] media;
}
