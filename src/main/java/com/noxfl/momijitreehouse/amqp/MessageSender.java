package com.noxfl.momijitreehouse.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Fernando Nathanael
 *
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private int messageCount = 0;

    public void send(String message) {
        rabbitTemplate.convertAndSend("leaf-rake", message);
        messageCount++;
        System.out.printf(" [%s] Sent message%n", messageCount);
    }

}
