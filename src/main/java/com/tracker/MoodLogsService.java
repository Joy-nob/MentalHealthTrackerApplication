package com.tracker;

import com.tracker.repository.MoodLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MoodLogsService {

	@Autowired
	private MoodLogsRepository moodLogRepository;

	// Fetch mood logs for the last 7 days
	public List<MoodLogs> getWeeklyMoodLogs(String username) {
		// Fetch the current logged-in user
		Long userId = getUserIdByUsername(username); // Updated to return Long

		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(7);

		return moodLogRepository.findMoodLogsForUserInLastWeek(userId, startDate, endDate);
	}

	private Long getUserIdByUsername(String username) {
		// Implement logic to fetch the userId based on the username
		return 1L; // Placeholder
	}

	// Calculate the most frequent mood
	public String calculateMostFrequentMood(List<MoodLogs> moodLogs) {
		Map<String, Integer> moodCount = new HashMap<>();
		for (MoodLogs log : moodLogs) {
			moodCount.put(log.getMood(), moodCount.getOrDefault(log.getMood(), 0) + 1);
		}
		return Collections.max(moodCount.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

	// Calculate the highest mood score
	public int calculateHighestMoodScore(List<MoodLogs> moodLogs) {
		return moodLogs.stream().mapToInt(MoodLogs::getScore) // Ensure `MoodLogs` has `getScore`
				.max().orElse(0);
	}

	// Calculate the lowest mood score
	public int calculateLowestMoodScore(List<MoodLogs> moodLogs) {
		return moodLogs.stream().mapToInt(MoodLogs::getScore) // Ensure `MoodLogs` has `getScore`
				.min().orElse(0);
	}

	public List<String> getLast7DaysLabels() {
		return LocalDate.now().datesUntil(LocalDate.now().plusDays(1)).map(LocalDate::toString)
				.collect(Collectors.toList());
	}

	public List<Integer> getLast7DaysScores(List<MoodLogs> moodLogs) {
		List<Integer> scores = new ArrayList<>();
		for (int i = 6; i >= 0; i--) {
			LocalDate targetDate = LocalDate.now().minusDays(i);
			OptionalInt score = moodLogs.stream().filter(log -> log.getCreatedAt().toLocalDate().equals(targetDate)) // Use
																														// `getCreatedAt`
					.mapToInt(MoodLogs::getScore) // Ensure `MoodLogs` has `getScore`
					.max();
			scores.add(score.orElse(0));
		}
		return scores;
	}
}
