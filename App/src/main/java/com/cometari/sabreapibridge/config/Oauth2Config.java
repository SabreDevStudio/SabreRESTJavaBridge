package com.cometari.sabreapibridge.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class Oauth2Config {

	@Autowired
	private Environment environment;
	private String sabreBaseUrl;
	private String sabreOauth2Url;
    private String sabreClientId;
    private String sabreClientKey;
    	 
    @PostConstruct
	private void init(){
    	setSabreBaseUrl(environment.getProperty("BASE.URL"));
    	setSabreOauth2Url(environment.getProperty("API.URL"));
    	setSabreClientId(environment.getProperty("CLIENT.ID"));
    	setSabreClientKey(environment.getProperty("CLIENT.KEY"));   	
    }
    
    public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	public String getSabreClientId() {
		return sabreClientId;
	}
	public void setSabreClientId(String sabreClientId) {
		this.sabreClientId = sabreClientId;
	}
	public String getSabreClientKey() {
		return sabreClientKey;
	}
	public void setSabreClientKey(String sabreClientKey) {
		this.sabreClientKey = sabreClientKey;
	}
	
    public String getSabreOauth2Url() {
		return sabreOauth2Url;
	}
    
	public void setSabreOauth2Url(String sabreOauth2Url) {
		this.sabreOauth2Url = sabreOauth2Url;
	}
	
	public String getSabreBaseUrl() {
		return sabreBaseUrl;
	}

	public void setSabreBaseUrl(String sabreBaseUrl) {
		this.sabreBaseUrl = sabreBaseUrl;
	}
 
}
