package com.example.notification.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestPushMessage {
    String title;
    String body;

    @Builder
    public RequestPushMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }
}