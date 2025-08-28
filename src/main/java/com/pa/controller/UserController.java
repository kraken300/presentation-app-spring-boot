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

import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
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

}
