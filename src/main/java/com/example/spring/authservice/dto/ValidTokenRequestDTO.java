package com.example.spring.authservice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ValidTokenRequestDTO {
    private String token;
}
