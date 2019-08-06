package com.demo.rabbit.constans;

/**
 * @author LiYingChun
 * @date 2019/8/6
 * RabbitMQ常量
 */
public interface RabbitConst {
    /**
     * 一对一模式
     */
    String QUEUE_ONE = "queueOne";

    /**
     * 一对多模式
     */
    String QUEUE_MANY = "queue.many";

    /**
     * 实体类
     */
    String QUEUE_OBJECT = "queue.object";
}
