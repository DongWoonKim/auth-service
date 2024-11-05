package com.example.spring.authservice.service;

import com.example.spring.authservice.mapper.UserMapper;
import com.example.spring.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public int join(User user) {
        return userMapper.saveUser(user);
    }

}
