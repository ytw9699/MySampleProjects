package com.example.chatserver.config.redis;

import com.example.chatserver.service.RedisSubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisListenerContainer {

    private final ChannelTopic chatTopic;
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "chatListenerAdapter")
    public MessageListenerAdapter chatListenerAdapter(RedisSubService subscriber) {
        return new MessageListenerAdapter(subscriber, "chatSend");
    }

    @Bean
    RedisMessageListenerContainer redisContainer(@Qualifier("chatListenerAdapter") MessageListenerAdapter chatListenerAdapter) {//컨테이너 설정

        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(chatListenerAdapter, chatTopic);//구독한다
        return container;
    }
}
