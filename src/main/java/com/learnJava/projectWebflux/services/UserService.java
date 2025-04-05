package com.learnJava.projectWebflux.services;

import com.learnJava.projectWebflux.dtos.SignupRequestDTO;
import com.learnJava.projectWebflux.dtos.UserResponseDTO;
import com.learnJava.projectWebflux.models.User;
import com.learnJava.projectWebflux.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserResponseDTO> signup(SignupRequestDTO signupRequest) {
        // Check if username already exists
        return userRepository.existsByUsername(signupRequest.getUsername())
                .flatMap(usernameExists -> {
                    if (usernameExists) {
                        return Mono.error(new RuntimeException("Username already exists"));
                    }

                    // Check if email already exists
                    return userRepository.existsByEmail(signupRequest.getEmail());
                })
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(new RuntimeException("Email already exists"));
                    }

                    // Create new user
                    User user = User.builder()
                            .username(signupRequest.getUsername())
                            .email(signupRequest.getEmail())
                            .password(passwordEncoder.encode(signupRequest.getPassword()))
                            .firstName(signupRequest.getFirstName())
                            .lastName(signupRequest.getLastName())
                            .active(true)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    return userRepository.save(user);
                })
                .map(this::mapToUserResponseDTO);
    }

    // Mapper method to convert User to UserResponseDTO
    private UserResponseDTO mapToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .active(user.getActive())  // This matches the field name 'active' in the DTO
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())  // This matches the field name 'updatedAt' in the DTO
                .build();
    }
}