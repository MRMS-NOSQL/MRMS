package com.example.movieweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movieweb.dto.request.SignInRequest;
import com.example.movieweb.dto.request.SignUpRequest;
import com.example.movieweb.dto.respose.JwtAuthenticationResponse;
import com.example.movieweb.dto.respose.SignUpResponse;
import com.example.movieweb.entity.Role;
import com.example.movieweb.entity.User;
import com.example.movieweb.repository.UserRepository;
import com.example.movieweb.service.AuthenticationService;
import com.example.movieweb.service.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public SignUpResponse signup(SignUpRequest signUpRequest) {
		/**
		 * Check if the email is already registered
		 */
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new IllegalArgumentException("Email has already been registered!");
		}
		/**
		 * Validate email and password format
		 */
		if (!isValidEmail(signUpRequest.getEmail()) || !isValidPassword(signUpRequest.getPassword())) {
			throw new IllegalArgumentException("Invalid email or password format!");
		}
		/**
		 * Create a new user and set information from SignUpRequest
		 */
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		/**
		 * Save the user to the MongoDB database
		 */
		User savedUser = userRepository.save(user);

		if (savedUser != null) {
			return new SignUpResponse("You have successfully registered.", savedUser.getId().toString());
		} else {
			throw new RuntimeException("User registration failed. Please try again.");
		}
	}

	@Override
	public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
		var user = userRepository.findByEmail(signInRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Email does not exist! "));

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		} catch (AuthenticationException e) {
			throw new IllegalArgumentException("Invalid email or password!");
		}
		var jwt = jwtService.generateToken(user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		return jwtAuthenticationResponse;
	}

	public boolean isValidEmail(String email) {
		return email != null && email.contains("@") && email.contains(".");
	}

	public boolean isValidPassword(String password) {
		return password != null && password.length() >= 4;
	}

}
