package com.fernandocanabarro.whatsapp_clone_app_backend.chat.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.dtos.ChatResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.services.ChatService;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.dtos.StringResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chats", description = "Endpoints for managing chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<StringResponseDto> createChat(
            @RequestParam(name = "sender-id") String senderId, 
            @RequestParam(name = "receiver-id") String receiverId) {
        String chatId = this.chatService.createChat(senderId, receiverId);
        return ResponseEntity.ok(new StringResponseDto(chatId));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getChatsByReceiverId(Authentication authentication) {
        return ResponseEntity.ok(this.chatService.getChatsByReceiverId(authentication));
    }

}
