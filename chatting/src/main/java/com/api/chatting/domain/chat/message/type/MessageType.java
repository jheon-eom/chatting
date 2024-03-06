package com.api.chatting.domain.chat.message.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {

    TEXT("text"),

    PICTURE("picture")
    ;

    private final String type;
}
