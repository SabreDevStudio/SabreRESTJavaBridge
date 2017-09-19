package com.cometari.sabreapibridge.domain;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cometari.sabreapibridge.config.Oauth2Config;

@Component
public class Oauth2Client {

	 @Autowired
	 private Oauth2Config oauth2Config;
	
	HttpEntity<String> requestAccessToken() {
		String id = Base64.encodeBase64String(oauth2Config.getSabreClientId().getBytes());
		String key = Base64.encodeBase64String(oauth2Config.getSabreClientKey().getBytes());
		String credentials = Base64.encodeBase64String((id + ":" + key).getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		headers.add("Authorization", "Basic " + credentials);
		HttpEntity<String> request = new HttpEntity<String>("grant_type=client_credentials", headers);		
		return request;
	}
	
	public AccessToken executeAccessTokenRequest() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());		
		ResponseEntity<AccessToken> response = restTemplate.postForEntity(oauth2Config.getSabreOauth2Url(), requestAccessToken(), AccessToken.class);	
		AccessToken accessToken = response.getBody();
		return accessToken;
	}

}
