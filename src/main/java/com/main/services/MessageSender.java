package com.main.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
	
	  private static final Logger LOGGER =
		      LoggerFactory.getLogger(MessageSender.class);

		  @Autowired
		  private KafkaTemplate<String, String> kafkaTemplate;

		  public void send(String payload) {
		    LOGGER.info("sending payload='{}'", payload);
		    kafkaTemplate.send("helloworld.t", payload);
		  }

}
