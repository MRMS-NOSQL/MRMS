package com.example.movieweb.dto.respose;

public class SignUpResponse {
	private String message;
	private String verificationToken;

	public SignUpResponse(String message, String verificationToken) {
		this.message = message;
		this.verificationToken = verificationToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
}
