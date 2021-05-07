package com.example.app.controllers;

import com.example.app.controllers.requests.RegistrationRequest;
import com.example.app.controllers.responses.RegistrationResponse;
import com.example.app.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class UserRegistrationController {

    public final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest request) {
        var confirmationToken =  registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationResponse(confirmationToken));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
