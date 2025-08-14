package com.chat.blip.controller;

import com.chat.blip.dto.GroupCreateRequest;
import com.chat.blip.entity.ChatGroup;
import com.chat.blip.entity.Message;
import com.chat.blip.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ChatGroup createGroup(@RequestBody GroupCreateRequest req, Principal principal) {
        return groupService.createGroup(req.getName(), principal.getName());
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@PathVariable String groupId, Principal principal) {
        groupService.joinGroup(groupId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/leave")
    public ResponseEntity<?> leaveGroup(@PathVariable String groupId, Principal principal) {
        groupService.leaveGroup(groupId, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/messages")
    public List<Message> getHistory(@PathVariable String groupId) {
        return groupService.getMessageHistory(groupId);
    }
}
