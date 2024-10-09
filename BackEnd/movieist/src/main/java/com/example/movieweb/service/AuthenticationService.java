package com.example.movieweb.service;

import com.example.movieweb.dto.request.SignInRequest;
import com.example.movieweb.dto.request.SignUpRequest;
import com.example.movieweb.dto.respose.JwtAuthenticationResponse;
import com.example.movieweb.dto.respose.SignUpResponse;

public interface AuthenticationService {
	SignUpResponse signup(SignUpRequest signUpRequest);
	JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
