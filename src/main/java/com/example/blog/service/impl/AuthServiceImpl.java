package com.example.blog.service.impl;

import com.example.blog.dto.LoginDto;
import com.example.blog.dto.RegisterDto;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.exception.BlogAPIException;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.response.JWTAuthResponse;
import com.example.blog.security.JwtTokenProvider;
import com.example.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
           loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String expireDate = jwtTokenProvider.getExpiration(token);

        return new JWTAuthResponse(token, expireDate);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> role = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        role.add(userRole);
        user.setRoles(role);

        userRepository.save(user);
        return "User registered successfully!";
    }
}
