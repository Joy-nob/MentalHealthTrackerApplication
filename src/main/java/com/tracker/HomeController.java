package com.tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class HomeController {

	// Redirect to Home Page after login
	@GetMapping("/home")
	public String homePage(Model model, @AuthenticationPrincipal UserDetails user) {
		// Get the logged-in user's username
		String username = user.getUsername(); // Assuming 'UserDetails' is your user entity

		// Pass the username to the frontend
		model.addAttribute("username", username);

		return "home"; // Return the home page
	}

	// Back Button to redirect to Home
	@GetMapping("/back-to-home")
	public String backToHome() {
		return "redirect:/home"; // Redirect to the home page
	}
}
