package com.example.spring.authservice.dto;

import com.example.spring.authservice.enums.Role;
import com.example.spring.authservice.enums.Token;
import com.example.spring.authservice.model.User;
import lombok.Getter;

import java.time.Duration;

@Getter
public class UserTokenRequestDTO {
    private String userId;
    private String password;
    private String userName;
    private Role role;
    private Token token;

    public User toUser() {
        return User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .role(role)
                .build();
    }

    public Duration tokenType() {
        return token == Token.ACCESS_TOKEN ?
                Duration.ofHours(2) :
                Duration.ofDays(2);
    }

}
