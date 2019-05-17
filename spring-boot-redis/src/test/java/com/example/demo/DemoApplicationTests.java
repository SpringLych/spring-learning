package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test() throws Exception{
        redisTemplate.opsForValue().set("boot", "good");
        Assert.assertEquals("good", redisTemplate.opsForValue().get("boot"));
    }

}
