package com.example.app.controllers.responses;

import lombok.Data;

@Data
public class RegistrationResponse {

    private String token;

    public RegistrationResponse(String token) {
        this.token = token;
    }
}
