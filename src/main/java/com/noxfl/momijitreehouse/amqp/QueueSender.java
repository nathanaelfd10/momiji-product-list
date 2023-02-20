/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Fernando Nathanael
 *
 */
public class QueueSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private FanoutExchange fanout;

	public void send(String message) {
		rabbitTemplate.convertAndSend(fanout.getName(), "", message);
		System.out.println(" [x] Sent message");
	}

}
