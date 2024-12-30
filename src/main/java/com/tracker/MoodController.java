package com.tracker;

import com.tracker.repository.MoodLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class MoodController {

	@Autowired
	private MoodLogsRepository moodLogsRepository;

	@GetMapping("/log-mood") // Use this to retrieve the list of moods
	public String getMoods(Model model) {
		model.addAttribute("log-mood", moodLogsRepository.findAll());
		return "log-mood"; // Return the mood list page
	}

	@PostMapping("/add-mood") // Adds a new mood log
	public String addMood(@RequestParam String mood, @RequestParam String notes) {
		MoodLogs newMoodLog = new MoodLogs(mood, 0, notes, LocalDateTime.now(), null);
		moodLogsRepository.save(newMoodLog);
		return "redirect:/log-mood"; // Redirect to mood list after adding
	}
}
