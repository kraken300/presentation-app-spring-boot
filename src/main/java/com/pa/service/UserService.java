package com.pa.service;

import org.springframework.http.ResponseEntity;

import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;

public interface UserService {

	ResponseEntity<String> registerUser(UserRequestDTO userRequestDTO);

	ResponseEntity<String> loginUser(UserLoginDTO userLoginDTO);

}
