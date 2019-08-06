package com.demo.rabbit.object;

import com.demo.model.User;
import com.demo.rabbit.constans.RabbitConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * 实体类接受
 */
@Component
@RabbitListener(queues = RabbitConst.QUEUE_OBJECT)
public class ObjectReceiver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(User user) {
        logger.info("-----receive user: {} -----", user.toString());
    }
}
