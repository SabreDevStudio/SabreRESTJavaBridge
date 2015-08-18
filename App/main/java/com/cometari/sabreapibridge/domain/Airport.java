package com.cometari.sabreapibridge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Airport{
	
	private String code;
	private String name;
	private String city;
	private String country;
	private String latitude;
	private String longitude;
			
	@JsonCreator
    public Airport(@JsonProperty("code") String VENDOR_CODE,@JsonProperty("name") String POI_NAME, @JsonProperty("city") String CITY_NAME,
    		@JsonProperty("country") String COUNTRY_CODE, @JsonProperty("latitude") String LATITUDE, @JsonProperty("longitude") String LONGITUDE) {
        this.code = VENDOR_CODE;
        this.name = POI_NAME;
        this.city = CITY_NAME;
        this.country = COUNTRY_CODE;
        this.latitude = LATITUDE;
        this.longitude = LONGITUDE;
    }
	
	public Airport() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}