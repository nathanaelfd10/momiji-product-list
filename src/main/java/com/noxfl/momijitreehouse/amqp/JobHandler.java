/**
 * 
 */
package com.noxfl.momijitreehouse.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author Fernando Nathanael
 *
 */

public class JobHandler {
	
	@RabbitListener(queues = "treehouse")
	public void jobReceiver(String in) throws InterruptedException {
		receive(in);
	}
	
	private void receive(String in) {
		System.out.println("Job received: " + in);
	}

}
