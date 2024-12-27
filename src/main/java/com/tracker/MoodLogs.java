package com.tracker;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MoodLogs")
public class MoodLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String mood;

	@Column
	private String notes;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "score") // Add this field
	private int score;

	public MoodLogs(String mood, int score, String notes, LocalDateTime createdAt, User user) {
		this.mood = mood;
		this.score = score;
		this.notes = notes;
		this.createdAt = createdAt;
		this.user = user;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getScore() { // Add getter
		return score;
	}

	public void setScore(int score) { // Add setter
		this.score = score;
	}
}
