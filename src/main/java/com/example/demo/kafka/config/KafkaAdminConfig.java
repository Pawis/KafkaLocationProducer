package com.example.demo.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {

	@Value(value = "${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServers;


	@Bean
	public KafkaAdmin kafkaAdmin() {

		Map<String, Object> configs = new HashMap<>();

		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(configs);
	}

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
