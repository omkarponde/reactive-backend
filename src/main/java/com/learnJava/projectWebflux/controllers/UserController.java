package com.learnJava.projectWebflux.controllers;

import com.learnJava.projectWebflux.dtos.AuthResponseDTO;
import com.learnJava.projectWebflux.dtos.LoginRequestDTO;
import com.learnJava.projectWebflux.dtos.SignupRequestDTO;
import com.learnJava.projectWebflux.dtos.UserResponseDTO;
import com.learnJava.projectWebflux.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<UserResponseDTO>> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        return userService.signup(signupRequest)
                .map(userResponseDTO -> ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO))
                .onErrorResume(error ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new UserResponseDTO())) // Empty DTO with error status
                );
    }

//    @GetMapping("/login")
//    public Mono<ResponseEntity<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
//
//    }
}