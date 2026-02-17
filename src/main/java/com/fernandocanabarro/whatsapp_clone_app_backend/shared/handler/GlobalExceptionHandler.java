package com.fernandocanabarro.whatsapp_clone_app_backend.shared.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fernandocanabarro.whatsapp_clone_app_backend.shared.dtos.StandardErrorDto;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDto> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(404).body(new StandardErrorDto(Instant.now(), status.value(), ex.error, ex.getMessage(), request.getRequestURI()));
    }
    
}
