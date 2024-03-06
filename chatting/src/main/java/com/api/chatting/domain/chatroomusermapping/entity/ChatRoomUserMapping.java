package com.api.chatting.domain.chatroomusermapping.entity;

import com.api.chatting.common.entity.BaseEntity;
import com.api.chatting.domain.chat.chatroom.entity.ChatRoom;
import com.api.chatting.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "chatroom_user_mapping")
@Entity
public class ChatRoomUserMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomUserMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
