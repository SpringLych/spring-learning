package com.demo.rabbit.hello;

import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * 发送者
 */
@Component
public class HelloSender {
    private final AmqpTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HelloSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(){
        String context = "send hello" + new Date();
        logger.info("Send: {}", context);
        this.rabbitTemplate.convertAndSend(RabbitConst.QUEUE_ONE, context);
    }
}
