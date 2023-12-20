package com.example.APICSEAIMLIOT.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//This configuration class defines the security rules for the application.
@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration {

	// This method creates the SecurityFilterChain bean, which enforces the security rules.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
	 // Configure HTTP security:
	 // 1. Require authentication for all requests.
	 http.authorizeHttpRequests(auth -> auth
			 .anyRequest().authenticated());
	
	 // 2. Enable basic authentication with default configuration.
	 http.httpBasic(withDefaults());
	 
	
	 // 3. Disable CSRF protection for simplicity (consider enabling in production).
	 http.csrf(csrf -> csrf.disable());
	
	 // Build and return the SecurityFilterChain.
	 return http.build();
	}
}

