package com.fernandocanabarro.whatsapp_clone_app_backend.message.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos.MessageRequestDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.dtos.MessageResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.services.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequestDto request) {
        this.messageService.saveMessage(request);
    }

    @PostMapping(name = "/upload-media", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMedia(
        @RequestParam(name = "chat-id") String chatId,
        @RequestParam MultipartFile file,
        Authentication authentication
    ) {
        this.messageService.uploadMediaMessage(chatId, file, authentication);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setMessagesToSeenByChat(
        @RequestParam(name = "chat-id") String chatId,
        Authentication authentication
    ) {
        this.messageService.setMessagesToSeenByChat(chatId, authentication);
    }

    @GetMapping("chat/{chatId}")
    public ResponseEntity<List<MessageResponseDto>> getChatMessages(@PathVariable String chatId) {
        return ResponseEntity.ok(this.messageService.getChatMessages(chatId));
    }

}
