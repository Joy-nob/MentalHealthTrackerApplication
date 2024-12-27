package com.tracker.repository;

import com.tracker.MoodLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MoodLogsRepository extends JpaRepository<MoodLogs, Integer> {
	@Query("SELECT m FROM MoodLogs m WHERE m.user.id = :userId AND m.createdAt BETWEEN :startDate AND :endDate ORDER BY m.createdAt DESC")
	List<MoodLogs> findMoodLogsForUserInLastWeek(@Param("userId") Long userId, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
}
