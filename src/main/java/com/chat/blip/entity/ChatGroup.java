package com.chat.blip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
    @Table(name = "chat_group")
    public class ChatGroup {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String groupId;

        private String name;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "group_members",
                joinColumns = @JoinColumn(name = "group_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
        private Set<User> members = new HashSet<>();


    }

