package com.example.movieweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieweb.dto.request.SignInRequest;
import com.example.movieweb.dto.request.SignUpRequest;
import com.example.movieweb.dto.respose.ErrorResponse;
import com.example.movieweb.dto.respose.JwtAuthenticationResponse;
import com.example.movieweb.dto.respose.SignUpResponse;
import com.example.movieweb.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
		try {
			SignUpResponse signupResponse = authenticationService.signup(signUpRequest);
			return ResponseEntity.ok(signupResponse);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Signup failed: " + e.getMessage()));
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
		try {
			JwtAuthenticationResponse signInResponse = authenticationService.signin(signInRequest);
			return ResponseEntity.ok(signInResponse);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Internal Server Error"));
		}
	}

}
