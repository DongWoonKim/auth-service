package com.example.spring.authservice.service;

import com.example.spring.authservice.dto.RefreshTokenResponseDTO;
import com.example.spring.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProviderService tokenProviderService;

    public RefreshTokenResponseDTO refreshToken(String refreshToken) {
        int result = tokenProviderService.validToken(refreshToken);
        String accessToken = null;
        String newRefreshToken = null;

        if (result == 1){
            User user = tokenProviderService.getTokenDetails(refreshToken);

            // Refresh Token이 유효하면 새로운  Token을 발급
            accessToken = tokenProviderService.generateToken(user, Duration.ofHours(2));

            // Refresh Token 생성 (긴 유효기간)
            newRefreshToken = tokenProviderService.generateToken(user, Duration.ofDays(2));
        }
        return RefreshTokenResponseDTO.builder()
                .status(result)
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
