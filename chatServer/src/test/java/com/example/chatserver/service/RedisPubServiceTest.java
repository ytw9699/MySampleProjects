package com.example.chatserver.service;

import com.example.chatserver.model.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisPubServiceTest {

    @Autowired
    RedisPubService redisPubService;

    @Test
    public void redisPublish(){

        ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setContent("test content");
                    chatMessage.setSender("test sender");

        redisPubService.sendMessage(chatMessage);
    }
}