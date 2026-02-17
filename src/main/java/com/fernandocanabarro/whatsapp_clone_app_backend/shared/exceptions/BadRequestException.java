package com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions;

public class BadRequestException extends RuntimeException {
    public final String error = "Bad Request";
    public BadRequestException(String message) {
        super(message);
    }
}