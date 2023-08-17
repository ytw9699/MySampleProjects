package com.sample.redis.controller;

import com.sample.redis.model.ChatMessage;
import com.sample.redis.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisPubService redisPubService;

    @PostMapping("message")//채팅 메시지 보내기
    public String redisPublish(@RequestBody ChatMessage chatMessage) {

        redisPubService.sendMessage(chatMessage);

        return "success";
    }
}
