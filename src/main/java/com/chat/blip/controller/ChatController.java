package com.chat.blip.controller;

import com.chat.blip.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    // Inject the SimpMessagingTemplate to send messages to subscribers
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // Handle messages sent to "/app/hello" (as per the frontend example)
    @MessageMapping("/hello")
    public void handleChatMessage(ChatMessage message) {
        // Send the message to the topic (this will broadcast to all subscribers of "/topic/messages")
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }
}
