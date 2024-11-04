package com.example.spring.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenResponseDTO {
    private String token;
}
