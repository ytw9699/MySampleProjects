package com.example.notification.controller;

import com.example.notification.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class FcmControllerTest {

    @Value("${fcm-notice-topic}")
    String noticeTopic;

    @Autowired
    private FcmService fcmService;

    @DisplayName("fcm 토픽으로 공지 알림 보내기")
    @Test
    public void pushNoticeMessage() throws FirebaseMessagingException {

        String title ="testTitle";
        String body ="testBody";

        Message msg = fcmService.createTopicMessage(noticeTopic, title, body);

        fcmService.sendMessage(msg);
    }
}
