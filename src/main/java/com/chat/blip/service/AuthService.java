package com.chat.blip.service;

import com.chat.blip.dto.AuthRequest;
import com.chat.blip.dto.RegisterRequest;
import com.chat.blip.entity.User;
import com.chat.blip.repository.UserRepository;
import com.chat.blip.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void register(RegisterRequest req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .enabled(true)
                .build();
        userRepository.save(user);
    }

    public String authenticate(AuthRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(req.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
