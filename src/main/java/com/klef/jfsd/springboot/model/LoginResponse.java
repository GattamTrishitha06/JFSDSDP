package com.klef.jfsd.springboot.model;

public class LoginResponse {
    private String message;
    private String role;
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LoginResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }
}