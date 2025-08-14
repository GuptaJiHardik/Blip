package com.chat.blip.controller;

import com.chat.blip.dto.ChatMessageDTO;
import com.chat.blip.entity.ChatGroup;
import com.chat.blip.entity.Message;
import com.chat.blip.repository.MessageRepository;
import com.chat.blip.repository.UserRepository;
import com.chat.blip.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GroupService groupService;
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO dto, Principal principal) {
        String username = principal.getName();

        ChatGroup group = groupService.findByGroupId(dto.getGroupId());
        if (!group.getMembers().stream().anyMatch(u -> u.getUsername().equals(username))) {

            return;
        }

        Message msg = new Message();
        msg.setGroup(group);
        msg.setSender(userRepo.findByUsername(username).orElseThrow());
        msg.setContent(dto.getContent());
        msg.setTimestamp(LocalDateTime.now());
        messageRepo.save(msg);

        ChatMessageDTO out = new ChatMessageDTO(dto.getGroupId(), username, dto.getContent(), msg.getTimestamp());
        messagingTemplate.convertAndSend("/topic/groups/" + dto.getGroupId(), out);
    }
}
