package com.tracker;

import com.tracker.repository.GoalRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GoalService {

	@Autowired
	private GoalRepository goalRepository;

	// In GoalService
	public Goal createGoal(User user, String description, LocalDateTime targetDate) {
		Goal goal = new Goal(user, description, targetDate);
		Goal savedGoal = goalRepository.save(goal);
		System.out.println("Goal saved: " + savedGoal); // Debug log
		return savedGoal;
	}

	// Get all goals for a specific user
	public List<Goal> getUserGoals(Long userId) {
		return goalRepository.findByUserId(userId);
	}

	// Mark a goal as achieved
	public Goal markGoalAsAchieved(Long goalId) {
		Goal goal = goalRepository.findById(goalId).orElseThrow();
		goal.setAchieved(true);
		return goalRepository.save(goal);
	}

	// Update a goal (e.g., change description or target date)
	public Goal updateGoal(Long goalId, String newDescription, LocalDateTime newTargetDate) {
		Goal goal = goalRepository.findById(goalId).orElseThrow();
		goal.setDescription(newDescription);
		goal.setTargetDate(newTargetDate);
		return goalRepository.save(goal);
	}

	// Delete a goal
	public void deleteGoal(Long goalId) {
		goalRepository.deleteById(goalId);
	}

}
