package com.pa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pa.dao.UserDAO;
import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.dto.UserResponseDTO;
import com.pa.entity.User;
import com.pa.enums.Role;
import com.pa.enums.Status;

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

	@Override
	public ResponseEntity<?> getUserById(Integer id) {
		User user = userDAO.findById(id);

		if (user.getStatus() == Status.ACTIVE) {
			UserResponseDTO responseDTO = new UserResponseDTO();
			BeanUtils.copyProperties(user, responseDTO);
			return new ResponseEntity<UserResponseDTO>(responseDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Cannot fetch for INACTIVE users", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> getAllUsers(Integer id) {
		User user = userDAO.findById(id);

		if (user.getRole() == Role.ADMIN) {

			List<User> users = userDAO.findAll();

			List<UserResponseDTO> responseDTOs = new ArrayList<UserResponseDTO>();

			for (User singleUser : users) {
				UserResponseDTO dto = new UserResponseDTO();

				if (singleUser.getRole() == Role.STUDENT) {
					BeanUtils.copyProperties(singleUser, dto);
					responseDTOs.add(dto);
				}
			}
			return new ResponseEntity<List<UserResponseDTO>>(responseDTOs, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("This operation can be done by ADMIN only!", HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public ResponseEntity<String> updateStatus(Integer adminId, Integer userId, Status status) {
		User admin = userDAO.findById(adminId);

		if (admin.getRole() == Role.ADMIN) {
			User user = userDAO.findById(userId);
			user.setStatus(status);
			User updatedUser = userDAO.save(user);
			return new ResponseEntity<String>("Status updated to : " + updatedUser.getStatus(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("This operation can be done by ADMIN only!", HttpStatus.UNAUTHORIZED);
		}
	}

}
