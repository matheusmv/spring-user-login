package com.example.app.repositories;

import com.example.app.entities.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
    @Query(value = "{token: ?0}")
    Optional<ConfirmationToken> findByToken(String token);

    @Query(value = "{email: ?0}")
    Optional<ConfirmationToken> findTokenByUserEmail(String email);
}
