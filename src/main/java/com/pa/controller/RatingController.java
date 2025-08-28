package com.pa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pa.dto.RatingRequestDTO;
import com.pa.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	// ADMIN CAN RATE THE PRESENTATION BY STUDENT ID AND PRESENTATION ID (BY ADMIN
	// ONLY)
	@PostMapping("/rate/{id}")
	public ResponseEntity<?> ratePresentation(@PathVariable(name = "id") Integer adminId,
			@RequestParam(name = "id") Integer studentId, @RequestParam Integer pid,
			@RequestBody RatingRequestDTO ratingRequestDTO) {
		return ratingService.ratePresentation(adminId, studentId, pid, ratingRequestDTO);
	}

	// GET THE RATING OF PARTICULAR PRESENTATION
	@GetMapping("/getRating/{pid}")
	public ResponseEntity<?> getRating(@PathVariable Integer pid) {
		return ratingService.getRatingByPresentationId(pid);
	}

	// GET OVERALL PRESENTATION RATING OF PARTICULAR STUDENT
	@GetMapping("/getOverallRating/{id}")
	public ResponseEntity<?> getOverallRating(@PathVariable Integer id) {
		return ratingService.getOverallRatingByStudentId(id);
	}

}
