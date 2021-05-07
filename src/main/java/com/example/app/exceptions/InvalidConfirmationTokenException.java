package com.example.app.exceptions;

import java.io.Serial;

public class InvalidConfirmationTokenException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidConfirmationTokenException(String message) {
        super(message);
    }
}
