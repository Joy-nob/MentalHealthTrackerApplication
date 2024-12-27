package com.tracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/set-goal")
public class GoalController {

	@Autowired
	private GoalService goalService;

	/**
	 * Display goals and serve the "Set Goal" page with the form.
	 */
	@GetMapping
	public String getUserGoalsAndForm(@AuthenticationPrincipal User user, Model model) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}

		List<Goal> goals = goalService.getUserGoals(user.getId());
		System.out.println("Fetched goals for user: " + goals); // Debug log
		model.addAttribute("goals", goals);
		return "setGoal";
	}

	/**
	 * Handle form submission for creating a new goal.
	 */
	@PostMapping
	public String createGoal(@AuthenticationPrincipal User user, @RequestParam String description,
			@RequestParam int days) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}

		// Calculate target date and create the goal
		LocalDate targetDate = LocalDate.now().plusDays(days);
		try {
			goalService.createGoal(user, description, targetDate.atStartOfDay());
		} catch (Exception e) {
			System.err.println("Error creating goal: " + e.getMessage());
			return "redirect:/set-goal?error=true"; // Redirect with error indication
		}

		return "redirect:/set-goal"; // Redirect back to the same page to show updated goals
	}

	/**
	 * Mark a goal as achieved.
	 */
	@PostMapping("/mark-achieved/{goalId}")
	public String markGoalAsAchieved(@AuthenticationPrincipal User user, @PathVariable Long goalId) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}

		try {
			goalService.markGoalAsAchieved(goalId);
		} catch (Exception e) {
			System.err.println("Error marking goal as achieved: " + e.getMessage());
		}
		return "redirect:/set-goal";
	}

	/**
	 * Update a goal's description and target date.
	 */
	@PostMapping("/update/{goalId}")
	public String updateGoal(@AuthenticationPrincipal User user, @PathVariable Long goalId,
			@RequestParam String description, @RequestParam String targetDate) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}

		try {
			LocalDateTime target = LocalDateTime.parse(targetDate);
			goalService.updateGoal(goalId, description, target);
		} catch (Exception e) {
			System.err.println("Error updating goal: " + e.getMessage());
		}
		return "redirect:/set-goal";
	}

	/**
	 * Delete a goal.
	 */
	@PostMapping("/delete/{goalId}")
	public String deleteGoal(@AuthenticationPrincipal User user, @PathVariable Long goalId) {
		if (user == null) {
			return "redirect:/login"; // Ensure the user is logged in
		}

		try {
			goalService.deleteGoal(goalId);
		} catch (Exception e) {
			System.err.println("Error deleting goal: " + e.getMessage());
		}
		return "redirect:/set-goal";
	}
}
