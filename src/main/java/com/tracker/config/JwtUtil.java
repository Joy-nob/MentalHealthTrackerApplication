package com.tracker.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

	// Secure key generation for HS256
	private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure 256-bit key
	private final long expirationTime = 1000 * 60 * 60 * 10; // 10 hours

	// Generate token based on username
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(secretKey) // Use the
																											// secure
																											// key here
				.compact();
	}

	// Extract username from token
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey) // Use the secure key for parsing
				.parseClaimsJws(token).getBody().getSubject();
	}

	// Validate token
	public boolean validateToken(String token) {
		return !extractUsername(token).isEmpty() && !isTokenExpired(token);
	}

	// Check if token is expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extract expiration date from token
	private Date extractExpiration(String token) {
		return Jwts.parser().setSigningKey(secretKey) // Use the secure key for parsing
				.parseClaimsJws(token).getBody().getExpiration();
	}
}
