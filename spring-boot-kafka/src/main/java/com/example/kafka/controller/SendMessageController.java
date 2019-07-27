package com.example.kafka.controller;

import com.example.kafka.engine.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiYingChun
 * @date 2019/7/27
 */
@Service
@RestController
public class SendMessageController {
    private final Producer producer;

    @Autowired
    SendMessageController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("send/{message}")
    public void send(@PathVariable String message) {
        this.producer.sendMessage(message);
    }
}
