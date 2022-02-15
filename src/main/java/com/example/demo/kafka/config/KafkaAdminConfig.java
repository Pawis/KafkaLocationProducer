package com.example.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaAdminConfig {
	
	@Bean
	public NewTopic newTopic() {
		
		return TopicBuilder
				.name("gps5")
				.partitions(6)
				.replicas(3)
				.config("min.insync.replicas", "3")
				.build();
	}
}
