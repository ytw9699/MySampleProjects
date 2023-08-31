package com.example.botpush.controller;

import com.example.botpush.telegram.Telegram;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BotPushController {

    private final Telegram telegram;

    @GetMapping("/telegram/send")
    public String testTelegramSend(String message) {
        telegram.send(message);
        return "success";
    }
}
