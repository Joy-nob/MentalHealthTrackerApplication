package com.tracker;

import com.tracker.repository.SelfAppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfAppreciationService {

	@Autowired
	private SelfAppreciationRepository selfAppreciationRepository;

	public List<SelfAppreciation> getAllAppreciationsByUser(Long userId) {
		return selfAppreciationRepository.findByUserId(userId);
	}

	public SelfAppreciation addAppreciation(SelfAppreciation appreciation) {
		return selfAppreciationRepository.save(appreciation);
	}
}