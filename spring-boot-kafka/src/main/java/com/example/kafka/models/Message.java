package com.example.kafka.models;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiYingChun
 * @date 2019/7/27
 */
@Data
public class Message implements Serializable {
    private String form;
    private String message;

    public Message() {
    }

    public Message(String form, String message) {
        this.form = form;
        this.message = message;
    }
}
