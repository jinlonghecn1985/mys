package com.jing.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

//import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

	private static final Logger logger = LoggerFactory.getLogger(Listener.class);
	
//	public CountDownLatch countDownLatch0 = new CountDownLatch(2);
//	public CountDownLatch countDownLatch1 = new CountDownLatch(2);
//	
//	@KafkaListener(id = "id0", group="group1", topicPartitions = { @TopicPartition(topic ="test", partitions = { "0" }) })
//	public void listenPartition0(ConsumerRecord<?, ?> record) throws ClassNotFoundException {
//		logger.debug("Received: " + record);
//		countDownLatch0.countDown();
//	}
	
	
	@KafkaListener(id = "id0", topics="test")
	public void listenPartition0(ConsumerRecord<?, ?> record) {
		logger.debug("Received: " + record);
	}
	
}
