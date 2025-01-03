package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.UserJoinRequestDTO;
import com.example.spring.authservice.dto.UserJoinResponseDTO;
import com.example.spring.authservice.dto.UserLoginRequestDTO;
import com.example.spring.authservice.dto.UserLoginResponseDTO;
import com.example.spring.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class UserApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public UserJoinResponseDTO join(@RequestBody UserJoinRequestDTO requestDTO) {
        log.info("join");
        return userService.join(requestDTO.toUser(bCryptPasswordEncoder));
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO requestDTO) {
        log.info("login");
        return userService.login(requestDTO.getUserId(), requestDTO.getPassword());
    }

}
