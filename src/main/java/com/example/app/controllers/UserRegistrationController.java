package com.example.app.controllers;

import com.example.app.controllers.requests.RegistrationRequest;
import com.example.app.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class UserRegistrationController {

    public final RegistrationService registrationService;

    @PostMapping
    public String registration(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
