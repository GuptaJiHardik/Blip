package com.chat.blip.service;

import com.chat.blip.entity.ChatGroup;
import com.chat.blip.entity.Message;
import com.chat.blip.entity.User;
import com.chat.blip.repository.ChatGroupRepository;
import com.chat.blip.repository.MessageRepository;
import com.chat.blip.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final ChatGroupRepository groupRepo;
    private final UserRepository userRepo;
    private final MessageRepository messageRepository;

    public ChatGroup createGroup(String name, String createdByUsername) {
        ChatGroup group = new ChatGroup();
        group.setName(name);
        group.setGroupId(generateUniqueCode()); // e.g. UUID or random string
        User creator = userRepo.findByUsername(createdByUsername).orElseThrow();
        group.getMembers().add(creator);
        return groupRepo.save(group);
    }

    public void joinGroup(String groupId, String username) {
        ChatGroup group = groupRepo.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        User user = userRepo.findByUsername(username).orElseThrow();
        group.getMembers().add(user);
        groupRepo.save(group);
    }

    public void leaveGroup(String groupId, String username) {
        ChatGroup group = groupRepo.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        User user = userRepo.findByUsername(username).orElseThrow();
        group.getMembers().remove(user);
        groupRepo.save(group);
    }

    public List<Message> getMessageHistory(String groupId) {
        ChatGroup group = groupRepo.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return messageRepository.findAllByGroupOrderByTimestampAsc(group);
    }

    private String generateUniqueCode() {
        return java.util.UUID.randomUUID().toString().substring(0, 8);
    }

    public ChatGroup findByGroupId(String groupId) {
        return groupRepo.findByGroupId(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }


}
