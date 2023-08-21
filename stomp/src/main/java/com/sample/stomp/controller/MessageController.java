package com.sample.stomp.controller;

import com.sample.stomp.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")//app/chat/message 라우팅
    public void sendChatMessage(ChatMessage message) { //첫번째 방식

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }

        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message); //pubish
    }

    @MessageMapping("/chat/message2/{roomId}")//app/chat/message 라우팅
    @SendTo("/topic/chat/room/{roomId}")
    public ChatMessage sendChatMessage2(@DestinationVariable String roomId, @RequestBody ChatMessage message) { //두번째 방식
        System.out.println(roomId);
        return message;
    }
}
