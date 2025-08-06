package com.pa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pa.dao.PresentationDAO;
import com.pa.dao.UserDAO;
import com.pa.dto.PresentationRequestDTO;
import com.pa.dto.UserLoginDTO;
import com.pa.dto.UserRequestDTO;
import com.pa.dto.UserResponseDTO;
import com.pa.entity.Presentation;
import com.pa.entity.User;
import com.pa.enums.PresentationStatus;
import com.pa.enums.Role;
import com.pa.enums.Status;
import com.pa.rs.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PresentationDAO presentationDAO;

	@Override
	public ResponseEntity<?> registerUser(UserRequestDTO userRequestDTO) {

		Optional<User> isUserPresent = userDAO.findByEmail(userRequestDTO.getEmail());

		if (isUserPresent.isPresent()) {
			return new ResponseEntity<String>("User is already registered!", HttpStatus.BAD_REQUEST);
		} else {
			User user = new User();
			BeanUtils.copyProperties(userRequestDTO, user);
			User saved = userDAO.save(user);
			ResponseStructure<String> rs = new ResponseStructure<String>("User registered successfully with email : ",
					saved.getEmail(), HttpStatus.CREATED);
			return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
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
			ResponseStructure<UserResponseDTO> rs = new ResponseStructure<UserResponseDTO>("User fetched successfully!",
					responseDTO, HttpStatus.OK);
			return new ResponseEntity<ResponseStructure<UserResponseDTO>>(rs, HttpStatus.OK);
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

			ResponseStructure<List<UserResponseDTO>> rs = new ResponseStructure<List<UserResponseDTO>>(
					"Users fetched successfully!", responseDTOs, HttpStatus.OK);
			return new ResponseEntity<ResponseStructure<List<UserResponseDTO>>>(rs, HttpStatus.OK);
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

	@Override
	public ResponseEntity<?> assignPresentation(Integer adminId, Integer userId,
			PresentationRequestDTO presentationRequestDTO) {
		User admin = userDAO.findById(adminId);

		if (admin.getRole() == Role.ADMIN) {
			User user = userDAO.findById(userId);

			Presentation presentation = new Presentation();
			BeanUtils.copyProperties(presentationRequestDTO, presentation);
			presentation.setUser(user);
//			User saved = userDAO.save(user);
			Presentation saved = presentationDAO.save(presentation);

			return new ResponseEntity<String>("Presentation assigned successfully!", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("This operation can be done by ADMIN only!", HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public ResponseEntity<?> getPresentationById(Integer pid) {
		Presentation presentation = presentationDAO.findById(pid);
		ResponseStructure<Presentation> rs = new ResponseStructure<Presentation>("Presentation fetched succesfully!",
				presentation, HttpStatus.OK);
		return new ResponseEntity<ResponseStructure<Presentation>>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllPresentations(Integer id) {
		User user = userDAO.findById(id);

		if (user.getRole() == Role.STUDENT) {
			List<Presentation> presentations = user.getPresentations();

			if (presentations.isEmpty()) {
				return new ResponseEntity<String>("No presentation is assigned yet!", HttpStatus.OK);
			}

			ResponseStructure<List<Presentation>> rs = new ResponseStructure<List<Presentation>>(
					"Presentations fetched successfully!", presentations, HttpStatus.OK);
			return new ResponseEntity<ResponseStructure<List<Presentation>>>(rs, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Presentations are assigned to students only!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> changePresentationStatus(Integer studentId, Integer pid,
			PresentationStatus presentationStatus) {

		User user = userDAO.findById(pid);

		if (user.getRole() == Role.STUDENT) {
			Presentation presentation = presentationDAO.findById(pid);
			presentation.setPresentationStatus(presentationStatus);
			presentationDAO.save(presentation);

			ResponseStructure<PresentationStatus> rs = new ResponseStructure<PresentationStatus>(
					"Presentaion status updated to ", presentation.getPresentationStatus(), HttpStatus.OK);
			return new ResponseEntity<ResponseStructure<PresentationStatus>>(rs, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Presentations are assigned to students only!", HttpStatus.BAD_REQUEST);
		}
	}

}
