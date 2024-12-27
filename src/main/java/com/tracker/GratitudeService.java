package com.tracker;

import com.tracker.repository.GratitudeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GratitudeService {

	@Autowired
	private GratitudeRepository gratitudeRepository;

	public List<Gratitude> getAllGratitudesByUser(Long userId) {
		return gratitudeRepository.findByUserId(userId);
	}

	public Gratitude addGratitude(Gratitude gratitude) {
		return gratitudeRepository.save(gratitude);
	}
}
