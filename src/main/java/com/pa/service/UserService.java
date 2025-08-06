package com.pa.service;

import org.springframework.http.ResponseEntity;

import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.enums.Status;

public interface UserService {

	ResponseEntity<String> registerUser(UserRequestDTO userRequestDTO);

	ResponseEntity<String> loginUser(UserLoginDTO userLoginDTO);

	ResponseEntity<?> getUserById(Integer id);

	ResponseEntity<?> getAllUsers(Integer id);

	ResponseEntity<String> updateStatus(Integer adminId, Integer userId, Status status);

}
