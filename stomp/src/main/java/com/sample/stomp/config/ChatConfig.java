package com.sample.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/stomp").setAllowedOriginPatterns("*").withSockJS();//웹소켓 핸드쉐이크를 위한 엔드포인트
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/queue", "/topic");
        //내장 브로거 사용, 거치지 않고 바로 구독자한테 메시지 전송 topic은 여러명, queue는 1명을 보통 의미하여 쓰임

        registry.setApplicationDestinationPrefixes("/app");
        //브로커로 보내기전에 가공을 위해 한번 거친다.
    }
}
