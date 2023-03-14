/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import org.springframework.amqp.core.FanoutExchange;
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

	public void send(String message) {
		rabbitTemplate.convertAndSend("leaf-rake", message);
		System.out.println(" [x] Sent message");
	}

}
