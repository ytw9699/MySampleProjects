package com.sample.redis.config;

import com.sample.redis.service.RedisSubService;
import com.sample.redis.model.ChatMessage;
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
    
    @Bean
    MessageListenerAdapter messageListenerAdapter() { //리스너어댑터 설정
        return new MessageListenerAdapter(new RedisSubService());
    }
    
    @Bean
    RedisMessageListenerContainer redisContainer() {//컨테이너 설정

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
                                      container.setConnectionFactory(redisConnectionFactory());
                                      container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {  //pub/sub 토픽 설정
        return new ChannelTopic("chat");
    }
}
