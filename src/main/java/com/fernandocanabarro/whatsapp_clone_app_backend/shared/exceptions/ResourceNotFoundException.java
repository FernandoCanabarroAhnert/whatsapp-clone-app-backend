package com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}