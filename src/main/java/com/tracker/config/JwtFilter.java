package com.tracker.config;

import com.tracker.CustomUserDetailsService;
import com.tracker.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final List<String> PUBLIC_ENDPOINTS = List.of("/login", "/signup", "/welcome", "/assets/");

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		// Skip token validation for public endpoints
		if (isPublicEndpoint(path)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extract and validate the token for secured endpoints
		String token = extractToken(request);
		if (token != null) {
			try {
				if (jwtUtil.validateToken(token)) {
					setAuthenticationContext(token);
				}
			} catch (JwtException e) {
				// Handle token-related exceptions (e.g., expired or invalid token)
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired token");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private boolean isPublicEndpoint(String path) {
		return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith) || path.matches("/assets/.*");
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private void setAuthenticationContext(String token) throws UsernameNotFoundException {
		String username = jwtUtil.extractUsername(token); // Extract username from token
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username); // Correct method call

		// Set authentication object in the security context
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
