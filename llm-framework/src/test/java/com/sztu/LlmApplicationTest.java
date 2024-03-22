package com.sztu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LlmApplicationTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void test() {
        List<String> str = new ArrayList<>();
        str.add("aaa");
        str.add("bbb");
        str.add("ccc");
        for(String s : str) {
            redisTemplate.opsForList().rightPushAll("my_string", s);
        }
    }
}
