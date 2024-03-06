package com.api.chatting.domain.chat.chatroom.entity;

import com.api.chatting.common.entity.BaseEntity;
import com.api.chatting.domain.chat.message.entity.Message;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Table(name = "chat_room")
@Entity
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @OneToMany(
            mappedBy = "chatRoom",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages;
}
