package com.tracker.repository;

import com.tracker.Gratitude;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GratitudeRepository extends JpaRepository<Gratitude, Integer> {
	List<Gratitude> findByUserId(Long userId);
}
