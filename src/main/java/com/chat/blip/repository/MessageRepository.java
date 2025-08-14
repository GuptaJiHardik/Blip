package com.chat.blip.repository;

import com.chat.blip.entity.ChatGroup;
import com.chat.blip.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByGroupOrderByTimestampAsc(ChatGroup group);
}