package com.main.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.main.entity.Customer;

public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("")
	private String exchange;
	
	@Value("")
	private String routingkey;
	
	public void send(Customer customer) {
		rabbitTemplate.convertAndSend(exchange, routingkey, customer);
		System.out.println("Send msg = " + customer);
	    
	}

}
