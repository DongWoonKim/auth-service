package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.UserJoinRequestDTO;
import com.example.spring.authservice.dto.UserJoinResponseDTO;
import com.example.spring.authservice.dto.UserTokenRequestDTO;
import com.example.spring.authservice.dto.UserTokenResponseDTO;
import com.example.spring.authservice.service.TokenProviderService;
import com.example.spring.authservice.service.UserService;
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

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public UserJoinResponseDTO join(@RequestBody UserJoinRequestDTO requestDTO) {
        return userService.join(requestDTO.toUser(bCryptPasswordEncoder)) == 1 ?
                UserJoinResponseDTO.builder()
                        .isSuccess(true)
                        .url("/webs/login")
                        .build() :
                UserJoinResponseDTO.builder()
                        .isSuccess(false)
                        .url("/webs/join")
                        .build();
    }

}
