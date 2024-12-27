package com.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tracker.repository")
public class MentalHealthTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalHealthTrackerApplication.class, args);

	}

}
