package com.learnJava.projectWebflux.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private final String tokenType = "Bearer";
}
