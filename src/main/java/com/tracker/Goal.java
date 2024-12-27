package com.tracker;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
public class Goal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user; // Associate goal with a user

	@Column(nullable = false, length = 255)
	private String description;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime targetDate;

	@Column(nullable = false)
	private boolean isAchieved;

	// Default constructor
	protected Goal() {
	}

	// Constructor with parameters
	public Goal(User user, String description, LocalDateTime targetDate) {
		this.user = user;
		this.description = description;
		this.targetDate = targetDate;
		this.isAchieved = false; // Default to false
	}

	// Automatically set createdAt before persisting
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDateTime targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isAchieved() {
		return isAchieved;
	}

	public void setAchieved(boolean achieved) {
		this.isAchieved = achieved;
	}
}
