package com.learnJava.projectWebflux.repositories;

import com.learnJava.projectWebflux.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

    Mono<User> findByUsername(String username);

    @Query("INSERT INTO users (id, username, email, password, first_name, last_name, active, created_at, updated_at) " +
            "VALUES (:id, :username, :email, :password, :firstName, :lastName, :active, :createdAt, :updatedAt) " +
            "RETURNING *")
    Mono<User> insertUser(UUID id, String username, String email, String password,
                          String firstName, String lastName, Boolean active,
                          LocalDateTime createdAt, LocalDateTime updatedAt);
}