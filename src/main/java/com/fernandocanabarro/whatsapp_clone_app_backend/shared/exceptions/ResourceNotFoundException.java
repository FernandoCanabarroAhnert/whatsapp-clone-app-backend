package com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public final String error = "Not Found";
    public ResourceNotFoundException(String message) {
        super(message);
    }
}