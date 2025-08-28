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

import com.pa.dto.PresentationRequestDTO;
import com.pa.enums.PresentationStatus;
import com.pa.service.PresentationService;

@RestController
@RequestMapping("/presentation")
public class PresentationController {

	@Autowired
	private PresentationService presentationService;

	// ASSIGN PRESENTATION TO THE STUDENT BASED ON THE STUDENT ID (ONLY BY ADMIN)
	@PostMapping("/assign/{id}")
	public ResponseEntity<?> assignPresentation(@PathVariable(name = "id") Integer adminId,
			@RequestParam(name = "id") Integer userId, @RequestBody PresentationRequestDTO presentationRequestDTO) {
		return presentationService.assignPresentation(adminId, userId, presentationRequestDTO);
	}

	// FETCH PRESENTATION BY ID
	@GetMapping("/getPresentation/{pid}")
	public ResponseEntity<?> getPresentationById(@PathVariable Integer pid) {
		return presentationService.getPresentationById(pid);
	}

	// FETCH ALL PRESENTATIONS BY STUDENT ID
	@GetMapping("/getAllPresentation/{id}")
	public ResponseEntity<?> getAllPresentationsById(@PathVariable Integer id) {
		return presentationService.getAllPresentations(id);
	}

	// CHANGE PRESENTATION STATUS (BY STUDENT)
	@PostMapping("/changeStatus/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable(name = "id") Integer studentId, @RequestParam Integer pid,
			@RequestParam PresentationStatus presentationStatus) {
		return presentationService.changePresentationStatus(studentId, pid, presentationStatus);
	}

	// SAVE TOTAL SCORE OF THE PRESENTAION (BY ADMIN ONLY)
	@PostMapping("/score/{id}")
	public ResponseEntity<?> saveTotalScore(@PathVariable(name = "id") Integer adminId, @RequestParam Integer pid,
			@RequestParam Double userTotalScore) {
		return presentationService.saveTotalScore(adminId, pid, userTotalScore);
	}
}
