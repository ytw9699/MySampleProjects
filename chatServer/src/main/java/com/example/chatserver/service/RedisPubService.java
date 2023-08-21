package com.example.chatserver.service;

import com.example.chatserver.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(ChatMessage chatMessage) {
        System.out.println("퍼블리시 하는 메시지 = " + chatMessage);
        redisTemplate.convertAndSend("chatSend", chatMessage);
    }
}
