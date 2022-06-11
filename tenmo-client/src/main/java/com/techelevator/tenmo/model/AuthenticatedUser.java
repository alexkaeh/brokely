package com.techelevator.tenmo.model;

public class AuthenticatedUser {
	
	private String token;
	private UserDTO user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
}
