package com.example.blog.controller;

import com.example.blog.dto.LoginDto;
import com.example.blog.dto.RegisterDto;
import com.example.blog.response.JWTAuthResponse;
import com.example.blog.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication"
)
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping({"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
//        String token = authService.login(loginDto);
//        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//        jwtAuthResponse.setAccessToken(token);

        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
