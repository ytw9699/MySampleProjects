package com.example.notification.controller;

import com.example.notification.dto.RequestPushMessage;
import com.example.notification.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping(value = "/push/{topic}") // fcm 푸시 알림 토픽 이용
    public void pushMessageWithTopic(@PathVariable("topic") String topic, RequestPushMessage request) throws FirebaseMessagingException {

        Message msg = fcmService.createTopicMessage(topic, request.getTitle(), request.getBody());

        fcmService.sendMessage(msg);
    }
}