package com.example.app.exceptions;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(String message) {
        super(message);
    }
}
