package com.example.app.services;

import com.example.app.entities.ConfirmationToken;
import com.example.app.entities.User;
import com.example.app.exceptions.UserExistsException;
import com.example.app.exceptions.UserNotFoundException;
import com.example.app.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "user with email %s not found";
    private static final String USER_ALREADY_EXISTS_MSG = "email already taken";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            // TODO: if email not confirmed send confirmation email again.
            throw new UserExistsException(USER_ALREADY_EXISTS_MSG);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        saveConfirmationToken(user, token);

        return token;
    }

    public void saveConfirmationToken(User user, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user.getEmail()
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public void enableUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MSG));

        user.setEnabled(true);
        userRepository.save(user);
    }
}
