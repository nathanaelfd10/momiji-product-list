/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Fernando Nathanael
 *
 */

public class MessageReceiver {

	public static final String INPUT_QUEUE_NAME = "tree-house";

	private MessageHandler messageHandler;

	@Autowired
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	@RabbitHandler
	@RabbitListener(queues = INPUT_QUEUE_NAME)
	public void receive(String message) throws JsonProcessingException {
		System.out.println("[*] Received new message");
		messageHandler.handle(message);
	}

}