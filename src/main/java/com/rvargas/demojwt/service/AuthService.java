package com.rvargas.demojwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rvargas.demojwt.model.AuthResponse;
import com.rvargas.demojwt.model.LoginRequest;
import com.rvargas.demojwt.model.RegisterRequest;
import com.rvargas.demojwt.model.Role;
import com.rvargas.demojwt.model.User;
import com.rvargas.demojwt.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		UserDetails user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
		String token = jwtService.getToken(user);
		return AuthResponse.builder()
				.token(token)
				.build();
	}

	public AuthResponse register(RegisterRequest registerRequest) {
		User user = User.builder()
				.username(registerRequest.getUsername())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.country(registerRequest.getCountry())
				.firstName(registerRequest.getFirstName())
				.lastName(registerRequest.getLastName())
				.role(Role.USER)
				.build();
		
		userRepository.save(user);
		
		return AuthResponse.builder()
				.token(jwtService.getToken(user))
				.build();
		
	}

}
