package com.pa.service;

import org.springframework.http.ResponseEntity;

import com.pa.dto.PresentationRequestDTO;
import com.pa.enums.PresentationStatus;

public interface PresentationService {
	ResponseEntity<?> assignPresentation(Integer adminId, Integer userId,
			PresentationRequestDTO presentationRequestDTO);

	ResponseEntity<?> getPresentationById(Integer pid);

	ResponseEntity<?> getAllPresentations(Integer id);

	ResponseEntity<?> changePresentationStatus(Integer studentId, Integer pid, PresentationStatus presentationStatus);

	ResponseEntity<?> saveTotalScore(Integer adminId, Integer pid, Double score);
}
