package com.fernandocanabarro.whatsapp_clone_app_backend.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.whatsapp_clone_app_backend.user.dtos.UserResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsersExceptSelf(Authentication authentication) {
        return ResponseEntity.ok(this.userService.getAllUsersExceptSelf(authentication));
    }

}
