package com.pa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// REGISTER USER
	@PostMapping
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
		return userService.registerUser(userRequestDTO);
	}

	// LOGIN USER
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
		return userService.loginUser(userLoginDTO);
	}
	
	

}
