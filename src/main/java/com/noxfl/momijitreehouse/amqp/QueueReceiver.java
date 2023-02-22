/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.noxfl.momijitreehouse.crawler.PageType;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;

/**
 * @author Fernando Nathanael
 *
 */

public class QueueReceiver {

	private SiteCrawlerFactory siteCrawlerFactory;

	public void setSiteCrawlerFactory(SiteCrawlerFactory siteCrawlerFactory) {
		this.siteCrawlerFactory = siteCrawlerFactory;
	}

	@RabbitListener(queues = "${queue.name.read}")
	public void jobReceiver(String in) throws InterruptedException {
		receive(in);
	}

	private void receive(String in) {
		System.out.println("Job received: " + in);

		JSONObject message = new JSONObject(in);

		siteCrawlerFactory.getSiteCrawler(null);
	}

}