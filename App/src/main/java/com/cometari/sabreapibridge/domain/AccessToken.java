package com.cometari.sabreapibridge.domain;

import java.io.Serializable;
import java.util.Date;


public class AccessToken implements Serializable{

	private static final long serialVersionUID = -7575921739812593608L;
	private String accessToken = null;
	private String tokenType = null;
	private Long expiresIn = null; 
	private Date date = null;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccess_token(String access_token) {
		this.accessToken = access_token;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setToken_type(String token_type) {
		this.tokenType = token_type;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpires_in(Long expires_in) {
		this.expiresIn = expires_in;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
