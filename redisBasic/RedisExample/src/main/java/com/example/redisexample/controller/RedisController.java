package com.example.redisexample.controller;

import com.example.redisexample.model.Message;
import com.example.redisexample.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/message")
    public Object insertMessage(@RequestBody Message message) {
        return redisService.insertMessage(message.getKey(), message.getContent());
    }
}