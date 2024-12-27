package com.tracker;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Gratitude")
public class Gratitude {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "gratitude_text", nullable = false, columnDefinition = "TEXT")
	private String gratitudeText;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	// Constructors
	public Gratitude() {
	}

	public Gratitude(Long userId, String gratitudeText) {
		this.userId = userId;
		this.gratitudeText = gratitudeText;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getGratitudeText() {
		return gratitudeText;
	}

	public void setGratitudeText(String gratitudeText) {
		this.gratitudeText = gratitudeText;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
