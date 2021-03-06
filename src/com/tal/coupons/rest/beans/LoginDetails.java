package com.tal.coupons.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.tal.coupons.enums.UserProfile;

@XmlRootElement
public class LoginDetails {
	
	private String username;
	private String password;
	private int clientType;
	
	public LoginDetails() {
		
	}
	
	public LoginDetails(String username, String password, int clientType) {
		this.setUsername(username);
		this.setPassword(password);
		this.setClientType(clientType);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "LoginDetails [username=" + username + ", password=" + password + ", clientType=" + clientType + "]";
	}
	
	

}
