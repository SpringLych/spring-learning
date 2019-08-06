package com.demo.rabbit.object;

import com.demo.model.User;
import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * 实体类发送类
 */
@Component
public class ObjectSender {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AmqpTemplate rabbitTemplate;

    public ObjectSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(User user) {
        logger.info("-----ObjectSender send user: {}-----", user.toString());
        rabbitTemplate.convertAndSend(RabbitConst.QUEUE_OBJECT, user);
    }
}
