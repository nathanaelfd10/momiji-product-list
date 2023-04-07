package com.noxfl.momijitreehouse.amqp;

import com.noxfl.momijitreehouse.model.schema.message.MomijiMessage;
import org.json.JSONObject;
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

    public void send(MomijiMessage momijiMessage) {

        String queueName = momijiMessage.getJob().isScrapeDetail() ? "product" : "extract";

        send(new JSONObject(momijiMessage).toString(), queueName);
    }

    public void send(String message, String queueName) {
        rabbitTemplate.convertAndSend(queueName, message);
        messageCount++;
        System.out.printf(" [%s] Sent message%n", messageCount);
    }

}
