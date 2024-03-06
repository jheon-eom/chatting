package com.api.chatting.domain.chat.message.entity;

import com.api.chatting.common.entity.BaseEntity;
import com.api.chatting.domain.chat.chatroom.entity.ChatRoom;
import com.api.chatting.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "message")
@Entity
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "type")
    private String messageType;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
