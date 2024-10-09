package com.example.movieweb.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	String extractUserName(String token);

	String generateToken(UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);

	boolean isTokenExpired(String token);

	String extractTokenFromHeader(String header);
}
