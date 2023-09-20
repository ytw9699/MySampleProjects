package com.example.notification.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    public Message createTopicMessage(String topic, String title, String body){//토픽 메시지 만들기

        Notification notification = Notification.builder().
                                                    setTitle(title).
                                                    setBody(body)
                                                .build();
        Message msg = Message.builder()
                                .setTopic(topic)
                                .setNotification(notification)
                             .build();
        return msg;
    }

    public void sendMessage(Message message) throws FirebaseMessagingException {//메시지 보내기
        log.info("Successfully sent message: " + FirebaseMessaging.getInstance().send(message));
    }
}
