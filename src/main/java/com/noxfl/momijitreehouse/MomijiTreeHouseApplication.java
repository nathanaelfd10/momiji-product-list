package com.noxfl.momijitreehouse;

import com.noxfl.momijitreehouse.amqp.MessageReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MomijiTreeHouseApplication {

	@Bean
	public Queue queue() {
		return new Queue("tree-house");
	}

	@Bean
	public MessageReceiver messageReceiver() {
		return new MessageReceiver();
	}

	@Bean
	public TreeHouseRunner treeHouseRunner() {
		return new TreeHouseRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(MomijiTreeHouseApplication.class, args);
	}

}
