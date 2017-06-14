package com.sxk.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey, String msg) {
        rabbitTemplate.convertAndSend(routingKey, msg);
    }
}
