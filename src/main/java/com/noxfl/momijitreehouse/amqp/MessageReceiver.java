/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;
import com.noxfl.momijitreehouse.model.Job;
import com.noxfl.momijitreehouse.model.MomijiMessage;
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

	private SiteCrawlerFactory siteCrawlerFactory;

	@Autowired
	public void setSiteCrawlerFactory(SiteCrawlerFactory siteCrawlerFactory) {
		this.siteCrawlerFactory = siteCrawlerFactory;
	}

	private Queue queue;

	@Autowired
	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	@RabbitHandler
	@RabbitListener(queues = INPUT_QUEUE_NAME)
	public void receiver(String message) throws InterruptedException, JsonProcessingException {
//		System.out.println("Job received: " + message);

		ObjectMapper objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		MomijiMessage momijiMessage = objectMapper.readValue(message, MomijiMessage.class);

		siteCrawlerFactory.getSiteCrawler(momijiMessage.getJob().getPageType()).fetchProducts(momijiMessage, 1, 100);
	}

}