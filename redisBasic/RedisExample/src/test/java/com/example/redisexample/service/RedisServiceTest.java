package com.example.redisexample.service;

import com.example.redisexample.model.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @DisplayName("String 값 insert, read 테스트")
    @Test
    public void insertStringMessage(){
        String key = "content";
        String content = "test content";
        redisService.insertMessage(key, content);
    }

    @DisplayName("객체 값 insert, read 테스트")
    @Test
    public void insertObjectMessage(){

        String key = "content";
        Message content = new Message();
                content.setContent("test content");
                content.setKey("test key");

        redisService.insertMessage(key, content);
    }

    @DisplayName("오직 String 값 insert, read 테스트")
    @Test
    public void insertOnlyStringMessage(){
        String key = "content2";
        String content = "test content2";
        redisService.insertOnlyStringMessage(key, content);
    }

    @DisplayName("int 값 insert, read 테스트")
    @Test
    public void insertIntMessage(){
        String key = "version";
        int content = 10;
        redisService.insertMessage(key, content);
    }

    @DisplayName("double값 증가 테스트")
    @Test
    public void incrementCount(){
        String key = "count";
        double count = 0.1;
        redisService.increment(key, count);
    }

    @DisplayName("랭킹 테스트")
    @Test
    public void updateDonationRank(){ //sorted set
        String key = "btc";
        String userNo = "2012121216";
        double score = 0.01;
        redisService.updateDonationRank(key, userNo, score);
    }

}