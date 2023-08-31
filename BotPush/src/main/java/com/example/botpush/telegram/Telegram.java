package com.example.botpush.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class Telegram {

    @Value("${telegram.token}")
    String telegramToken;

    @Value("${telegram.chatId}")
    String telegramChatId;

    public void send(String message) {

        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<String> test = restTemplate.postForEntity(
                "https://api.telegram.org/bot"+telegramToken+"/sendMessage",
                new Req(telegramChatId,  message),
                String.class
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        private String chat_id;
        private String text;
    }
}
