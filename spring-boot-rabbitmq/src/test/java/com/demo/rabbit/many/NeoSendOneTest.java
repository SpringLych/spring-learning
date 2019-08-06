package com.demo.rabbit.many;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author LiYingChun
 * @date 2019/8/6
 * 一对多发送测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NeoSendOneTest {

    @Autowired
    private NeoSendOne sendOne;

    @Test
    public void justSend() throws Exception {
        for (int i = 0; i < 30; ++i) {
            sendOne.send(i);
        }
    }
}