package com.example.kafka.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

/**
 * @author LiYingChun
 * @date 2019/7/27
 */
public class Consumer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "users")
    public void consumer(String message) throws IOException{
        logger.info("接受消息：{}", message);
    }
}
