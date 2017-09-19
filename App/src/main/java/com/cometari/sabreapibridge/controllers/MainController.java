package com.cometari.sabreapibridge.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cometari.sabreapibridge.domain.Airport;
import com.cometari.sabreapibridge.services.AirportService;
import com.cometari.sabreapibridge.services.SabreAPIService;


@Controller
@RequestMapping("")
public class MainController {
	
	static Logger log = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	SabreAPIService sabreAPIService;
	
	@Autowired
	AirportService airportsService;
	
	@RequestMapping(value="", method = RequestMethod.GET)	
	public @ResponseBody ResponseEntity<?> root(HttpServletRequest request){
		log.info(request.getRequestURL()+request.getQueryString());
		return new ResponseEntity<Object>("server is running",HttpStatus.OK);
	}

	@RequestMapping(value="api/**", method = RequestMethod.GET)	
	public @ResponseBody ResponseEntity<?> getMethod(HttpServletRequest request) throws IOException {
		log.info("Incoming GET Request: " + request.getRequestURL()+request.getQueryString());		
		String redirectURL = request.getServletPath().toString()+"?"+request.getQueryString();
		redirectURL = redirectURL.replace("/api/", "");
		return sabreAPIService.getRequest(redirectURL);
	}
	
	@RequestMapping(value="api/**", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody ResponseEntity<?> postMethod(HttpServletRequest request, HttpEntity<?> requestBody) throws IOException {		
		log.info("Incoming POST Request: " + request.getRequestURL()+request.getQueryString());
		String redirectURL = request.getServletPath().toString()+"?"+request.getQueryString();
		redirectURL = redirectURL.replace("/api/", "");
		return sabreAPIService.postRequest(redirectURL, requestBody);
	}
	
	@RequestMapping(value="airports", params={"text"}, method = RequestMethod.GET)	
	public @ResponseBody ResponseEntity<?> getAirportsByText(HttpServletRequest request,@RequestParam("text") String text) throws IOException {
		log.info("Incoming GET Request: " + request.getRequestURL()+request.getQueryString());
		return new ResponseEntity<List <Airport>>(airportsService.findAirportsByText(text.toLowerCase()),HttpStatus.OK);
	}
	
	@RequestMapping(value="airports", params={"lat","lng"}, method = RequestMethod.GET)	
	public @ResponseBody ResponseEntity<?> getAirportsByCoordiate(HttpServletRequest request,@RequestParam(value="lat", defaultValue="0.0") Double lat, @RequestParam(value="lng", defaultValue="0.0") Double lng) throws IOException {
		log.info("Incoming GET Request: " + request.getRequestURL()+request.getQueryString());
		return new ResponseEntity<Airport>(airportsService.findAirportsByCoordiate(String.valueOf(lat), String.valueOf(lng)),HttpStatus.OK);
	}
	
	
}
