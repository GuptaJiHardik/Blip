package com.chat.blip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Table(name = "message")
    public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        private User sender;

        @ManyToOne(optional = false)
        private ChatGroup group;

        @Column(nullable = false)
        private String content;

        @Column(nullable = false)
        private LocalDateTime timestamp = LocalDateTime.now();

    }

