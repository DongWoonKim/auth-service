package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.UserJoinRequestDTO;
import com.example.spring.authservice.dto.UserTokenRequestDTO;
import com.example.spring.authservice.dto.UserTokenResponseDTO;
import com.example.spring.authservice.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class UserApiController {

    private final TokenProviderService tokenProviderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/token")
    public ResponseEntity<UserTokenResponseDTO> token(UserTokenRequestDTO requestDTO) {
        String token = tokenProviderService.generateToken(requestDTO.toUser(), requestDTO.tokenType());

        return ResponseEntity.ok(
                UserTokenResponseDTO.builder()
                        .token(token)
                        .build()
        );
    }

    @PostMapping("/join")
    public boolean join(@RequestBody UserJoinRequestDTO requestDTO) {
        System.out.println("request dto :: " + requestDTO);
        return true;
    }

}
