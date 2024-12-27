package com.tracker.config;

import com.tracker.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final UserService userService;
	private final JwtFilter jwtFilter;

	// Constructor injection for UserService and JwtFilter
	public SecurityConfig(UserService userService, JwtFilter jwtFilter) {
		this.userService = userService;
		this.jwtFilter = jwtFilter;
	}

	// Define a PasswordEncoder bean
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Configure AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		// Use the UserService (which implements UserDetailsService) and
		// BCryptPasswordEncoder
		authenticationManagerBuilder.userDetailsService(userService) // Automatically use the UserService bean
				.passwordEncoder(passwordEncoder()); // Use passwordEncoder method for encoding

		return authenticationManagerBuilder.build();
	}

	// Configure SecurityFilterChain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // Disable CSRF for stateless authentication
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions
				.and()
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/welcome", "/login", "/signup", "/assets/**").permitAll() // Public endpoints
						.requestMatchers("/home", "/moods", "/add-mood", "/back-to-home").authenticated() // Secured
																											// endpoints
						.requestMatchers("/log-mood/**").authenticated().requestMatchers("/set-goal/**").authenticated()
						.requestMatchers("/gratitude/**").authenticated().requestMatchers("/self-appreciation/**")
						.authenticated().requestMatchers("/weekly-mood-analysis/**").authenticated().anyRequest()
						.authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JwtFilter before other
																							// filters

		return http.build();
	}
}
