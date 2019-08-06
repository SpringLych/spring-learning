package com.demo.rabbit.many;

import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author LiYingChun
 * @date 2019/8/6
 * 一对多
 */
@Component
public class NeoSendOne {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void send(int i) {
        String context = "many send " + i;
        logger.info("---NeoSendOne: {}-----", context);
        this.rabbitTemplate.convertAndSend(RabbitConst.QUEUE_MANY, context);
    }
}
