package com.example.app.exceptions;

import java.io.Serial;

public class EmailSenderException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailSenderException(String message) {
        super(message);
    }
}
