package com.fernandocanabarro.whatsapp_clone_app_backend.notification.models;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;
import com.fernandocanabarro.whatsapp_clone_app_backend.notification.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String content;
    private String senderId;
    private String receiverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;

}
