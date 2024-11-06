package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.*;
import com.example.spring.authservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public RefreshTokenResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
        return tokenService.refreshToken(requestDTO.getRefreshToken());
    }

    @PostMapping("/validToken")
    public ValidTokenResponseDTO validToken(@RequestBody ValidTokenRequestDTO requestDTO) {
        return tokenService.validToken(requestDTO.getRefreshToken());
    }

    @PostMapping("/authentication")
    public AuthenticationResponseDTO authentication(@RequestBody AuthenticationRequestDTO requestDTO) {
        return tokenService.getAuthentication(requestDTO.getToken());
    }

}
