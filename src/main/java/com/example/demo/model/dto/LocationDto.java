package com.example.demo.model.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

public class LocationDto {

	@NotNull
	@Range(min = 0, max = 9999999)
	private String deviceId;
	
	@NotNull
	@Digits(integer=7, fraction=0)
	private Integer latitiude;

	@NotNull
	@Digits(integer=7, fraction=0)
	private Integer longitude;

	public LocationDto(String deviceId, Integer latitiude, Integer longitude) {
		this.deviceId = deviceId;
		this.latitiude = latitiude;
		this.longitude = longitude;
	}


	public LocationDto() {
		super();
	}



	public String getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	public Integer getLatitiude() {
		return latitiude;
	}



	public void setLatitiude(Integer latitiude) {
		this.latitiude = latitiude;
	}



	public Integer getLongitude() {
		return longitude;
	}



	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}



}
