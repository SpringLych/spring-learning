package com.demo.rabbit.object;

import com.demo.model.User;
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
public class ObjectSenderTest {

    @Autowired
    private ObjectSender objectSender;

    @Test
    public void send() {
        User user = new User();
        user.setName("Jon");
        user.setPass("password");
        objectSender.send(user);
    }
}