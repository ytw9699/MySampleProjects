/*
package com.example.chatserver.config.redis;

import com.example.chatserver.model.ChatMessage;
import com.example.chatserver.service.RedisSubService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() { //redisTemplate 설정

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
                                      redisTemplate.setConnectionFactory(redisConnectionFactory());
                                      redisTemplate.setKeySerializer(new StringRedisSerializer());
                                      redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
        return redisTemplate;
    }

    @Bean(name = "chatListenerAdapter")
    public MessageListenerAdapter chatListenerAdapter(RedisSubService subscriber) {
        return new MessageListenerAdapter(subscriber, "chatSend");
    }

    @Bean
    RedisMessageListenerContainer redisContainer(@Qualifier("chatListenerAdapter") MessageListenerAdapter chatListenerAdapter) {//컨테이너 설정

        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
                                      container.setConnectionFactory(redisConnectionFactory());
                                      container.addMessageListener(chatListenerAdapter, chatTopic());//구독한다
        return container;
    }

    @Bean
    ChannelTopic chatTopic() {  //pub/sub 토픽 설정
        return new ChannelTopic("chatTopic");
    }
}
*/
