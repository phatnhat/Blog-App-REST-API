package com.example.blog.service;

import com.example.blog.dto.LoginDto;
import com.example.blog.dto.RegisterDto;
import com.example.blog.response.JWTAuthResponse;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
