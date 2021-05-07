package com.example.app.controllers.responses;

import com.example.app.entities.User;
import lombok.Data;

@Data
public class RegistrationResponse {

    private String id;

    public RegistrationResponse(User user) {
        this.id = user.getId();
    }
}
