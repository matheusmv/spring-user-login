package com.example.app.repositories;

import com.example.app.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{email: ?0}")
    Optional<User> findByEmail(String email);
}
