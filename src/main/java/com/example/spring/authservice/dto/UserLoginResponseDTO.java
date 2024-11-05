package com.example.spring.authservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private boolean isLoggedIn;
    private String url;
    private String userName;
    private String userId;
    private String message;
    private String accessToken;
    private String refreshToken;
}