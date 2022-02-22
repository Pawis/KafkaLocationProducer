package com.example.demo;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.dto.LocationDto;
import com.example.demo.serializer.LocationSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class SerializationTest {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void test_serialization_LocationSerializer() {
		
		LocationSerializer serializer = new LocationSerializer();
		
		LocationDto locationDto = new LocationDto("1",1,1);
		
		byte[] serializedLocationDto = serializer.serialize(null, locationDto);
		serializer.close();
		
		LocationDto deserializedLocationDto = null;
		try {
			deserializedLocationDto = mapper.readValue(serializedLocationDto, LocationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(locationDto.getDeviceId(), deserializedLocationDto.getDeviceId());
		Assertions.assertEquals(locationDto.getLongitude(), deserializedLocationDto.getLongitude());
		Assertions.assertEquals(locationDto.getLatitiude(), deserializedLocationDto.getLatitiude());
		
	}
	

}
