package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.demo.model.dto.LocationDto;


@Service
public class KafkaProducer {

	private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);


	private KafkaTemplate<String,LocationDto> locationKafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, LocationDto> kafkaTemplate) {
		this.locationKafkaTemplate = kafkaTemplate;
	}


	public void sendMessage(LocationDto location,String key) {
		log.info("Sending message...");
		this.locationKafkaTemplate.send("gps5",key,location).addCallback(new ListenableFutureCallback<SendResult<String,LocationDto>>() {

			@Override
			public void onSuccess(SendResult<String, LocationDto> result) {
				log.info("Sent sucessfully to kafka. Topic: {} Partition: {} ",result.getRecordMetadata().topic(),result.getRecordMetadata().partition());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Error when sending to kafka : ",ex);

			}

		});
	}

}
