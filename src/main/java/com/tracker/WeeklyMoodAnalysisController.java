package com.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WeeklyMoodAnalysisController {

	@Autowired
	private MoodLogsService moodLogService;

	@GetMapping("/weekly-analysis")
	public String getWeeklyAnalysis(Model model) {
		// Fetch the current logged-in user
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// Get the last 7 days' worth of mood logs for the current user
		List<MoodLogs> weeklyMoodLogs = moodLogService.getWeeklyMoodLogs(username);

		// Calculate the weekly mood analysis (frequent mood, highest mood score, lowest
		// mood score)
		String mostFrequentMood = moodLogService.calculateMostFrequentMood(weeklyMoodLogs);
		int highestScore = moodLogService.calculateHighestMoodScore(weeklyMoodLogs);
		int lowestScore = moodLogService.calculateLowestMoodScore(weeklyMoodLogs);

		// Pass the data to the frontend (Thymeleaf rendering)
		model.addAttribute("moodLogs", weeklyMoodLogs);
		model.addAttribute("mostFrequentMood", mostFrequentMood);
		model.addAttribute("highestScore", highestScore);
		model.addAttribute("lowestScore", lowestScore);

		return "weekly-analysis"; // Return the Thymeleaf template
	}

	@GetMapping("/api/mood-analysis")
	@ResponseBody
	public Map<String, Object> getMoodAnalysisData() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<MoodLogs> weeklyMoodLogs = moodLogService.getWeeklyMoodLogs(username);

		String mostFrequentMood = moodLogService.calculateMostFrequentMood(weeklyMoodLogs);
		int highestScore = moodLogService.calculateHighestMoodScore(weeklyMoodLogs);
		int lowestScore = moodLogService.calculateLowestMoodScore(weeklyMoodLogs);

		Map<String, Object> response = new HashMap<>();
		response.put("labels", moodLogService.getLast7DaysLabels());
		response.put("scores", moodLogService.getLast7DaysScores(weeklyMoodLogs));
		response.put("mostFrequentMood", mostFrequentMood);
		response.put("highestScore", highestScore);
		response.put("lowestScore", lowestScore);

		return response;
	}
}
