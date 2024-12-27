package com.tracker;

import com.tracker.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	// Handle user registration
	@PostMapping("/signup")
	public String registerUser(@RequestBody SignupRequest signupRequest, Model model) {
		try {
			userService.registerUser(signupRequest.getUsername(), signupRequest.getPassword(),
					signupRequest.getEmail());
			return "redirect:/login"; // Redirect to login after successful registration
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "signup"; // Stay on signup page if registration fails
		} catch (Exception e) {
			model.addAttribute("error", "An unexpected error occurred. Please try again.");
			return "signup";
		}
	}

	// Handle user login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			// Authenticate user using Spring Security
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			// Generate JWT token
			String token = jwtUtil.generateToken(loginRequest.getUsername());

			return ResponseEntity.ok(new AuthResponse(token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}

	// Response class for returning token
	public static class AuthResponse {
		private String token;

		public AuthResponse(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}
	}

	// DTO for Login Request
	public static class LoginRequest {
		private String username;
		private String password;

		// Getters and Setters
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	// DTO for Signup Request (for JSON body)
	public static class SignupRequest {
		private String username;
		private String password;
		private String email;

		// Getters and Setters
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
