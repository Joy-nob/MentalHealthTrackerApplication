package com.tracker.repository;

import com.tracker.SelfAppreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfAppreciationRepository extends JpaRepository<SelfAppreciation, Integer> {
	List<SelfAppreciation> findByUserId(Long userId);
}
