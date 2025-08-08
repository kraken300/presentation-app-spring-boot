package com.pa.service;

import org.springframework.http.ResponseEntity;

import com.pa.dto.PresentationRequestDTO;
import com.pa.dto.RatingRequestDTO;
import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.enums.PresentationStatus;
import com.pa.enums.Status;

public interface UserService {

	ResponseEntity<?> registerUser(UserRequestDTO userRequestDTO);

	ResponseEntity<String> loginUser(UserLoginDTO userLoginDTO);

	ResponseEntity<?> getUserById(Integer id);

	ResponseEntity<?> getAllUsers(Integer id);

	ResponseEntity<String> updateStatus(Integer adminId, Integer userId, Status status);

	ResponseEntity<?> assignPresentation(Integer adminId, Integer userId,
			PresentationRequestDTO presentationRequestDTO);

	ResponseEntity<?> getPresentationById(Integer pid);

	ResponseEntity<?> getAllPresentations(Integer id);

	ResponseEntity<?> changePresentationStatus(Integer studentId, Integer pid, PresentationStatus presentationStatus);

	ResponseEntity<?> saveTotalScore(Integer adminId, Integer pid, Double score);

	ResponseEntity<?> ratePresentation(Integer adminId, Integer studentId, Integer pid,
			RatingRequestDTO ratingRequestDTO);

	ResponseEntity<?> getRatingByPresentationId(Integer pid);

	ResponseEntity<?> getAllRatingsByStudentId(Integer id);

}
