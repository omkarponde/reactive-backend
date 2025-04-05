package com.learnJava.projectWebflux.controllers;

import com.learnJava.projectWebflux.dtos.PingDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ping")
public class PingController {

    @GetMapping
    public Mono<PingDTO> ping()
    {
        PingDTO pingResponse = new PingDTO();
        pingResponse.setMessage("pong");
        return Mono.just(pingResponse);
    }
}
