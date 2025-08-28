package com.pa.service;

import org.springframework.http.ResponseEntity;

import com.pa.dto.RatingRequestDTO;

public interface RatingService {
	ResponseEntity<?> ratePresentation(Integer adminId, Integer studentId, Integer pid,
			RatingRequestDTO ratingRequestDTO);

	ResponseEntity<?> getRatingByPresentationId(Integer pid);

	ResponseEntity<?> getOverallRatingByStudentId(Integer id);

}
