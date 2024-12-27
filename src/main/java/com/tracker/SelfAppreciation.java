package com.tracker;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SelfAppreciation")
public class SelfAppreciation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "appreciation_text", nullable = false, columnDefinition = "TEXT")
	private String appreciationText;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	// Constructors
	public SelfAppreciation() {
	}

	public SelfAppreciation(Long userId, String appreciationText) {
		this.userId = userId;
		this.appreciationText = appreciationText;
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

	public String getAppreciationText() {
		return appreciationText;
	}

	public void setAppreciationText(String appreciationText) {
		this.appreciationText = appreciationText;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
