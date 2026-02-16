package com.fernandocanabarro.whatsapp_clone_app_backend.user.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.fernandocanabarro.whatsapp_clone_app_backend.user.entities.User;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.mappers.UserMapper;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizerService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdentityProvider(Jwt token) {
        log.info("Synchronizing user with identity provider");
        this.getUserEmail(token).ifPresent(email -> {
            log.info("Synchronizing user having email {}", email);
            Optional<User> userOptional = this.userRepository.findByEmail(email);
            User user = this.userMapper.fromTokenAttributes(token.getClaims());
            userOptional.ifPresent(existingUser -> user.setId(existingUser.getId()));
            this.userRepository.save(user);
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }

}
