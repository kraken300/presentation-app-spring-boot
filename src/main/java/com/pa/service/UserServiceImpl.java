package com.pa.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pa.dao.UserDAO;
import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public ResponseEntity<String> registerUser(UserRequestDTO userRequestDTO) {

		Optional<User> isUserPresent = userDAO.findByEmail(userRequestDTO.getEmail());

		if (isUserPresent.isPresent()) {
			return new ResponseEntity<String>("User is already registered!", HttpStatus.BAD_REQUEST);
		} else {
			User user = new User();
			BeanUtils.copyProperties(userRequestDTO, user);
			userDAO.save(user);
			return new ResponseEntity<String>("User registered successfully!", HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<String> loginUser(UserLoginDTO userLoginDTO) {
		boolean isUserPresent = userDAO.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

		if (isUserPresent) {
			return new ResponseEntity<String>("Logged in successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Invalid credentials!", HttpStatus.BAD_REQUEST);
		}
	}

}
