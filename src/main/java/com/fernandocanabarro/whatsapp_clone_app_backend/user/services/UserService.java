package com.fernandocanabarro.whatsapp_clone_app_backend.user.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.whatsapp_clone_app_backend.user.dtos.UserResponseDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.mappers.UserMapper;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsersExceptSelf(Authentication authentication) {
        return userRepository.findAllUsersExceptSelf(authentication.getName()).stream()
                .map(this.userMapper::toUserResponseDto)
                .toList();
    }

}
