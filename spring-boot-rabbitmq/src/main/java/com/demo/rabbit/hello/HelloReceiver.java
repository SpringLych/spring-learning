package com.demo.rabbit.hello;

import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * 接收者
 */
@Component
@RabbitListener(queues = RabbitConst.QUEUE_ONE)
public class HelloReceiver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String hello) {
        logger.info("HelloReceive {}", hello);
    }
}
