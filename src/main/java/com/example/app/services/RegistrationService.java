package com.example.app.services;

import com.example.app.controllers.requests.RegistrationRequest;
import com.example.app.entities.ConfirmationToken;
import com.example.app.entities.User;
import com.example.app.entities.enums.UserRole;
import com.example.app.exceptions.InvalidConfirmationTokenException;
import com.example.app.exceptions.InvalidEmailException;
import com.example.app.services.utils.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String EMAIL_NOT_VALID_MSG = "email %s not valid";
    private static final String TOKEN_NOT_VALID_MSG = "token %s not valid";
    private static final String EMAIL_ALREADY_CONFIRMED_MSG = "email already confirmed";
    private static final String TOKEN_EXPIRED_MSG = "token expired";

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
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

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new InvalidConfirmationTokenException(String.format(TOKEN_NOT_VALID_MSG, token)));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new InvalidEmailException(EMAIL_ALREADY_CONFIRMED_MSG);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new InvalidConfirmationTokenException(TOKEN_EXPIRED_MSG);
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
