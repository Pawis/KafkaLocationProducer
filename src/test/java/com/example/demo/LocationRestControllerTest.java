package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.expections.ErrorResponse;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.model.dto.LocationDto;
import com.example.demo.rest.LocationRest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LocationRest.class )
public class LocationRestControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private ObjectMapper mapper ;
	
	@Test
	void valid_LocationDto() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("1",1,1);
		
		String json = mapper.writeValueAsString(locatiobDto);

		mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}
	
	@Test
	void empty_LocationDto() throws Exception {
		
		LocationDto locatiobDto = new LocationDto();
		
		String json = mapper.writeValueAsString(locatiobDto);

		mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());

	}
	
	@Test
	void latitiude_cannot_be_null() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("1",null,1);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("must not be null");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void longitude_cannot_be_null() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("1",1,null);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("must not be null");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void deviceId_must_be_a_number() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("a",1,1);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("must be between 0 and 9999999");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		System.out.println(actualErrorResponse);
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void deviceId_must_be_positive_number() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("-1",1,1);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("must be between 0 and 9999999");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}
	
	
	@Test
	void longitude_must_be_shother_than_7_numbers() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("1",12345678,1);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("numeric value out of bounds (<7 digits>.<0 digits> expected)");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void latitiude_must_be_shother_than_7_numbers() throws Exception {
		
		LocationDto locatiobDto = new LocationDto("1",1,12345678);
		
		String json = mapper.writeValueAsString(locatiobDto);

		MvcResult response = mvc.perform(MockMvcRequestBuilders
				.post("/kafka/location/publish")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("numeric value out of bounds (<7 digits>.<0 digits> expected)");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String actualErrorResponse = response.getResponse().getContentAsString();
		String expectedErrorResponse = mapper.writeValueAsString(errorResponse);
		
		Assertions.assertEquals(actualErrorResponse, expectedErrorResponse);
		
		Assertions.assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
	}

}
