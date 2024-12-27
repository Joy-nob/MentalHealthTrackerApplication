package com.tracker.repository;

import com.tracker.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
	// Get all goals for a specific user
	List<Goal> findByUserId(Long userId);
}
