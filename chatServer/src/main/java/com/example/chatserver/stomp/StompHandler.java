package com.example.chatserver.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("StompHandler accessor => {}", accessor);

        StompCommand command = accessor.getCommand();

        final String destination = accessor.getDestination();

        log.info("destination => {}", destination);
        log.info("simpSessionId => {}", accessor.getSessionId());

        if (command == null) {
            return message;
        }

        switch (command) {

            case CONNECT: {
                log.info("StompHandler - CONNECT => {}", message);
                break;
            }
            case SUBSCRIBE: {
                log.info("StompHandler - SUBSCRIBE => {}", message);
                break;
            }
            case UNSUBSCRIBE: {
                log.info("StompHandler - UNSUBSCRIBE => {}", message);
                break;
            }
            case DISCONNECT: {
                log.info("StompHandler - DISCONNECT {}", message);
                break;
            }
        }

        return message;
    }
}
