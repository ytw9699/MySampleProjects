package com.example.redisexample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public Object insertMessage(String key, Object content) {

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                                        valueOperations.set(key, content);

        Object result = valueOperations.get(key);

        System.out.println(result);

        return result;
    }

    public String insertOnlyStringMessage(String key, String content) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        valueOperations.set(key, content);

        String result = stringRedisTemplate.opsForValue().get(key);

        System.out.println(result);

        return result;
    }

    public void setRedisValue(String key, Object content) throws JsonProcessingException {

        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(content));
    }

    public <T> T getRedisValue(String key, Class<T> classType) throws JsonProcessingException {

        String redisValue = (String)redisTemplate.opsForValue().get(key);

        return objectMapper.readValue(redisValue, classType);
    }

    public double increment(String key, double count) {

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.increment(key, count);

        double result = (double)redisTemplate.opsForValue().get("count");

        System.out.println(result);

        return result;
    }

    public void updateDonationRank(String key, String userNo, double score) {
        redisTemplate.opsForZSet().incrementScore(key, userNo, score);

        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<Object>> zSetValuesWithScore = zSetOps.reverseRangeWithScores(key, 0, 9);

        for(ZSetOperations.TypedTuple<Object> result : zSetValuesWithScore){
            String user = (String) result.getValue();
            Double resultScore = result.getScore();
            System.out.println("user = " +user);
            System.out.println("score = " +BigDecimal.valueOf(resultScore).setScale(8, RoundingMode.FLOOR).stripTrailingZeros());
        }
    }

}
