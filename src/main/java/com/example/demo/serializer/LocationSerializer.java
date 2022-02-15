package com.example.demo.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.dto.LocationDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationSerializer implements Serializer<LocationDto>{
	
	private static final Logger log = LoggerFactory.getLogger(LocationSerializer.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, LocationDto data) {
		
		try {
			if(data== null) {
				log.warn("Cannot serialize null" + data);
				return null;
			}
			
			log.debug("Serializing...");
			return objectMapper.writeValueAsBytes(data);
			
		}catch (Exception e) {
			throw new SerializationException("Error when serializing Location to byte[]");
		}
		
	}

}
