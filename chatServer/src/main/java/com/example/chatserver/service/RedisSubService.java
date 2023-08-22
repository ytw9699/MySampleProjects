package com.example.chatserver.service;

import com.example.chatserver.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisSubService {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations sendingOperation;

    /**
     * Chat 메세지를 전송합니다.
     */
    public void chatSend(String publishMessage) throws JsonProcessingException {

        ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);

        log.info("RedisSubscriber - chatMessage => {}", chatMessage);

        sendingOperation.convertAndSend("/topic/chat/room/"+chatMessage.getRoomId(), chatMessage); //stomp pubish
    }
}
