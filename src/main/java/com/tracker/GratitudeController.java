package com.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gratitude")
public class GratitudeController {

	@Autowired
	private GratitudeService gratitudeService;

	// Display gratitude page with the list of gratitudes
	@GetMapping
	public String getGratitudes(@AuthenticationPrincipal User user, Model model) {
		if (user == null) {
			return "gratitude"; // Redirect to login if user is not logged in
		}
		List<Gratitude> gratitudes = gratitudeService.getAllGratitudesByUser(user.getId());
		model.addAttribute("gratitudes", gratitudes); // Add the list of gratitudes to the model
		return "gratitude"; // Render gratitude.html
	}

	// Handle form submission for adding a new gratitude
	@PostMapping
	public String addGratitude(@AuthenticationPrincipal User user, @RequestParam String gratitudeText) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}
		Gratitude gratitude = new Gratitude();
		gratitude.setUserId(user.getId());
		gratitude.setGratitudeText(gratitudeText);
		gratitudeService.addGratitude(gratitude);
		return "redirect:/gratitude"; // Redirect back to the gratitude page
	}

}
