package com.demo.rabbit.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author LiYingChun
 * @date 2019/8/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloSenderTest {
    @Autowired
    private HelloSender sender;

    @Test
    public void hello() throws Exception{
        sender.send();
    }
}