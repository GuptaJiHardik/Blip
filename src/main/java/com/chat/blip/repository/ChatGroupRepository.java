package com.chat.blip.repository;

import com.chat.blip.entity.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
    Optional<ChatGroup> findByGroupId(String groupId);
}