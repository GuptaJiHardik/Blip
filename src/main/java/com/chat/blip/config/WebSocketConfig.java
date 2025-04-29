package com.chat.blip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket endpoint for clients
                .setAllowedOriginPatterns("*") // Allow from all origins (later you can restrict for security)
                .withSockJS(); // Enable SockJS fallback for browsers that don't support WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Messages with /topic prefix will be broadcasted
        registry.setApplicationDestinationPrefixes("/app"); // Client will send messages to /app/*
    }
}
