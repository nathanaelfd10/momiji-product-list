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
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author Fernando Nathanael
 *
 */

public class MessageReceiver {

	public static final String INPUT_QUEUE_NAME = "tree-house";

	private SiteCrawlerFactory siteCrawlerFactory;

	public void setSiteCrawlerFactory(SiteCrawlerFactory siteCrawlerFactory) {
		this.siteCrawlerFactory = siteCrawlerFactory;
	}

	@RabbitListener(queues = INPUT_QUEUE_NAME)
	public void receiver(String in) throws InterruptedException, JsonProcessingException {
		receive(in);
	}

	private void receive(String message) throws JsonProcessingException {
		System.out.println("Job received: " + message);

		ObjectMapper objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


		MomijiMessage momijiMessage = objectMapper.readValue(message, MomijiMessage.class);

		Job job = momijiMessage.getJob();

		siteCrawlerFactory.getSiteCrawler(job.getPageType()).fetchProducts(job.getTargetUrl(), 1, 100);
	}

}