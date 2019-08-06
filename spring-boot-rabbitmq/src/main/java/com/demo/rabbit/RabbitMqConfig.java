package com.demo.rabbit;

import com.demo.rabbit.constans.RabbitConst;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiYingChun
 * @date 2019/8/6
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 一对一
     */
    @Bean
    public Queue queueOne() {
        return new Queue(RabbitConst.QUEUE_ONE);
    }

    /**
     * 用来测试一对多
     */
    @Bean
    public Queue queueMany() {
        return new Queue(RabbitConst.QUEUE_MANY);
    }

    /**
     * 对象的发送和接受
     */
    @Bean
    public Queue objectQueue() {
        return new Queue(RabbitConst.QUEUE_OBJECT);
    }
}
