package com.cometari.sabreapibridge.services;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cometari.sabreapibridge.config.Oauth2Config;
import com.cometari.sabreapibridge.domain.AccessToken;
import com.cometari.sabreapibridge.domain.Oauth2Client;
import com.cometari.sabreapibridge.handlers.ApiResponseErrorHandler;

@Service
public class SabreAPIService {

	static Logger log = Logger.getLogger(SabreAPIService.class.getName());

	@Autowired
	private Oauth2Config oauth2Config;

	@Autowired
	private Oauth2Client oauth2Client;

	@Autowired
	ApiResponseErrorHandler responseErrorHandler;

	private AccessToken accessToken;
	private String BASE_URL;

	@PostConstruct
	void init() {
		BASE_URL = oauth2Config.getSabreBaseUrl();
	}

	@PostConstruct
	void authorization() {
		accessToken = oauth2Client.executeAccessTokenRequest();
	}

	public ResponseEntity<?> getRequest(String redirectURL) {
		
		ResponseEntity<?> response = executeGetRequest(redirectURL);
		try {
			if (ApiResponseErrorHandler.isError(response.getStatusCode())) {
				throw new RestClientException(MessageFormat.format("Response from: {0}{1} HttpStatus: {2}", BASE_URL,redirectURL, response.getStatusCode()));
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			if(e.getMessage().contains("HttpStatus: 401")){
				authorization();
				response = executeGetRequest(redirectURL);
				log.info("Refresh AccessToken and Resend Request");
			}
		}
		log.info(MessageFormat.format("Response from: {0}{1} HttpStatus: {2}", BASE_URL,redirectURL, response.getStatusCode()));
		return response;
	}

	public ResponseEntity<?> postRequest(String redirectURL, HttpEntity<?> requestBody) {
		ResponseEntity<?> response = executePostRequest(redirectURL, requestBody);
		try {
			if (ApiResponseErrorHandler.isError(response.getStatusCode())) {
				throw new RestClientException(MessageFormat.format("Response from: {0}{1} HttpStatus: {2}", BASE_URL,redirectURL, response.getStatusCode()));
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			if(e.getMessage().contains("HttpStatus: 401")){
			authorization();
			response = executePostRequest(redirectURL, requestBody);
			log.info("Refresh AccessToken and Resend Request");
			}
		}
		log.info(MessageFormat.format("Response from: {0}{1} HttpStatus: {2}", BASE_URL,redirectURL, response.getStatusCode()));
		return response;
	}
	
	private ResponseEntity<?> executeGetRequest(String redirectURL) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken.getAccessToken());
		HttpEntity<String> request = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(responseErrorHandler);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ResponseEntity<?> response = restTemplate.exchange(BASE_URL + redirectURL, HttpMethod.GET, request, Map.class);
		return response;
	}
	
	private ResponseEntity<?> executePostRequest(String redirectURL, HttpEntity<?> requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken.getAccessToken());
		headers.add("Content-Type", "application/json");
		headers.add("Accept-Encoding", "gzip");
		HttpEntity<?> request = new HttpEntity<Object>(requestBody.getBody(), headers);
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(responseErrorHandler);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ResponseEntity<?> response = restTemplate.exchange(BASE_URL + redirectURL, HttpMethod.POST, request, Map.class);
		System.out.println(response.getBody());
		return response;
	}

}
