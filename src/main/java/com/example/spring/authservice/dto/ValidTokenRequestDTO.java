package com.example.spring.authservice.dto;

import lombok.Getter;

@Getter
public class ValidTokenRequestDTO {
    private String refreshToken;
}