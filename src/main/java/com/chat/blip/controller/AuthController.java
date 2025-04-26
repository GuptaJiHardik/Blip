package com.chat.blip.controller;

import com.chat.blip.dto.AuthRequest;
import com.chat.blip.dto.AuthResponse;
import com.chat.blip.dto.RegisterRequest;
import com.chat.blip.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request);
        return new AuthResponse(token);
    }

}
