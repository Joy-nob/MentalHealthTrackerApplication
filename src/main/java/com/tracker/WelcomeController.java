package com.tracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	// Display the welcome page
	@GetMapping("/welcome")
	public String welcomePage() {
		return "welcome"; // Returns the welcome page
	}

	// Display the login page
	@GetMapping("/login")
	public String loginPage() {
		return "login"; // Returns the login page
	}

	// Display the sign-up page
	@GetMapping("/signup")
	public String signupPage() {
		return "signup"; // Returns the sign-up page
	}
}
