package com.example.app.services;

import com.example.app.controllers.requests.RegistrationRequest;
import com.example.app.entities.User;
import com.example.app.entities.enums.UserRole;
import com.example.app.exceptions.InvalidEmailException;
import com.example.app.services.utils.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String EMAIL_NOT_VALID_MSG = "email %s not valid";

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new InvalidEmailException(String.format(EMAIL_NOT_VALID_MSG, request.getEmail()));
        }

        return userService.signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }
}
