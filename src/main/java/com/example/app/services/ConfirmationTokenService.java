package com.example.app.services;

import com.example.app.entities.ConfirmationToken;
import com.example.app.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        var confirmationToken = getToken(token);
        confirmationToken.ifPresent(tkn -> tkn.setConfirmedAt(LocalDateTime.now()));
        confirmationToken.ifPresent(confirmationTokenRepository::save);
    }
}
