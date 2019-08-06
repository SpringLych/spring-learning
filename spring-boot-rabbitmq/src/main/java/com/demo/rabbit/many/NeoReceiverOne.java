package com.demo.rabbit.many;

import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * 一对多测试
 */
@Component
@RabbitListener(queues = RabbitConst.QUEUE_MANY)
public class NeoReceiverOne {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String text) {
        logger.info("-------Receiver {}---------", text);
    }
}
