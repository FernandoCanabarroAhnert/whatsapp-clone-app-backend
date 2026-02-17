package com.fernandocanabarro.whatsapp_clone_app_backend.message.mappers;

import org.springframework.stereotype.Service;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos.MessageResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.entities.Message;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.services.BlobStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageMapper {

    private final BlobStorageService blobStorageService;

    public MessageResponseDto toMessageResponseDto(Message message) {
        MessageResponseDto response = new MessageResponseDto();
        response.setId(message.getId());
        response.setContent(message.getType() == MessageType.TEXT ? message.getContent() : null);
        response.setType(message.getType());
        response.setState(message.getState());
        response.setSenderId(message.getSenderId());
        response.setReceiverId(message.getReceiverId());
        response.setCreatedDate(message.getCreatedDate());
        response.setMedia(message.getType() == MessageType.TEXT ? null : this.blobStorageService.downloadBlob(message.getBlobName()));
        return response;
    }

}
