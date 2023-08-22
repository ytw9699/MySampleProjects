package com.example.chatserver.controller;

import com.example.chatserver.model.ChatMessage;
import com.example.chatserver.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RedisPubService redisPubService;

    @MessageMapping("/chat/message")//app/chat/message 라우팅
    public void sendChatMessage(ChatMessage message) { //첫번째 방식

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setContent(message.getSender()+"님이 입장하였습니다.");
        }

        redisPubService.sendChatMessage(message);
    }

    @MessageMapping("/chat/message2/{roomId}")//app/chat/message 라우팅
    public void sendChatMessage2(@DestinationVariable String roomId, @RequestBody ChatMessage message) { //두번째 방식
        redisPubService.sendChatMessage(message);
    }
}
