package com.pa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pa.dto.PresentationRequestDTO;
import com.pa.dto.RatingRequestDTO;
import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.enums.PresentationStatus;
import com.pa.enums.Status;
import com.pa.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// REGISTER USER
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
		return userService.registerUser(userRequestDTO);
	}

	// LOGIN USER
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
		return userService.loginUser(userLoginDTO);
	}

	// GET USER DETAILS BY ID (ONLY ACTIVE ONES)
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		return userService.getUserById(id);
	}

	// GET ALL USERS (ONLY BY ADMIN)
	@GetMapping("/getAll/{id}")
	public ResponseEntity<?> getAllUsers(@PathVariable Integer id) {
		return userService.getAllUsers(id);
	}

	// UPDATE STATUS (ONLY BY ADMIN)
	@PutMapping("/{id}")
	public ResponseEntity<String> updateStatus(@PathVariable(name = "id") Integer adminId,
			@RequestParam(name = "id") Integer userId, @RequestParam Status status) {
		return userService.updateStatus(adminId, userId, status);
	}

	// ASSIGN PRESENTATION TO THE STUDENT BASED ON THE STUDENT ID (ONLY BY ADMIN)
	@PostMapping("/assign/{id}")
	public ResponseEntity<?> assignPresentation(@PathVariable(name = "id") Integer adminId,
			@RequestParam(name = "id") Integer userId, @RequestBody PresentationRequestDTO presentationRequestDTO) {
		return userService.assignPresentation(adminId, userId, presentationRequestDTO);
	}

	// FETCH PRESENTATION BY ID
	@GetMapping("/getPresentation/{pid}")
	public ResponseEntity<?> getPresentationById(@PathVariable Integer pid) {
		return userService.getPresentationById(pid);
	}

	// FETCH ALL PRESENTATIONS BY STUDENT ID
	@GetMapping("/getAllPresentation/{id}")
	public ResponseEntity<?> getAllPresentationsById(@PathVariable Integer id) {
		return userService.getAllPresentations(id);
	}

	// CHANGE PRESENTATION STATUS (BY STUDENT)
	@PostMapping("/changeStatus/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable(name = "id") Integer studentId, @RequestParam Integer pid,
			@RequestParam PresentationStatus presentationStatus) {
		return userService.changePresentationStatus(studentId, pid, presentationStatus);
	}

	// SAVE TOTAL SCORE OF THE PRESENTAION (BY ADMIN ONLY)
	@PostMapping("/score/{id}")
	public ResponseEntity<?> saveTotalScore(@PathVariable(name = "id") Integer adminId, @RequestParam Integer pid,
			@RequestParam Double userTotalScore) {
		return userService.saveTotalScore(adminId, pid, userTotalScore);
	}

	// ************************* RATING *************************//

	// ADMIN CAN RATE THE PRESENTATION BY STUDENT ID AND PRESENTATION ID (BY ADMIN
	// ONLY)
	@PostMapping("/rate/{id}")
	public ResponseEntity<?> ratePresentation(@PathVariable(name = "id") Integer adminId,
			@RequestParam(name = "id") Integer studentId, @RequestParam Integer pid,
			@RequestBody RatingRequestDTO ratingRequestDTO) {
		return userService.ratePresentation(adminId, studentId, pid, ratingRequestDTO);
	}

	// GET THE RATING OF PARTICULAR PRESENTATION
	@GetMapping("/getRating/{pid}")
	public ResponseEntity<?> getRating(@PathVariable Integer pid) {
		return userService.getRatingByPresentationId(pid);
	}

	// GET ALL RATINGS OF PARTICULAR STUDENT
	@GetMapping("/getAllRating/{id}")
	public ResponseEntity<?> getAllRatings(@PathVariable Integer id) {
		return userService.getAllRatingsByStudentId(id);
	}

}
