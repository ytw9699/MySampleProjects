package com.example.chatserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    // 채팅방 입장 화면
    @GetMapping("")
    public String roomDetail(Model model, Integer roomId) {

        if(roomId == null){
            Random random = new Random();
            roomId = random.nextInt(1000);
        }

        model.addAttribute("roomId", roomId);

        return "/chat/detail";
    }
}
