package com.example.chatserver.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class RedisTopic {

    @Bean
    ChannelTopic chatTopic() {  //pub/sub 토픽 설정
        return new ChannelTopic("chatTopic");
    }
}
