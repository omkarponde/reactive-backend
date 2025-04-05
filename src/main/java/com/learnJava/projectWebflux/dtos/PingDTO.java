package com.learnJava.projectWebflux.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PingDTO {
    @JsonProperty("ping")
    private String message = "pong";
}
