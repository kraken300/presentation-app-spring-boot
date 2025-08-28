package com.pa.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pa.dao.PresentationDAO;
import com.pa.dao.UserDAO;
import com.pa.dto.PresentationRequestDTO;
import com.pa.entity.Presentation;
import com.pa.entity.User;
import com.pa.enums.PresentationStatus;
import com.pa.enums.Role;
import com.pa.rs.ResponseStructure;

@Service
public class PresentationServiceImpl implements PresentationService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PresentationDAO presentationDAO;

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
			return new ResponseEntity<String>("Presentation with pid : " + saved.getPid()
					+ " is assigned successfully to student with id : " + user.getId(), HttpStatus.OK);

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

		User user = userDAO.findById(studentId);

		// TODO : ADD logic so that one student cannot change other student's
		// presentation status
		if (user.getRole() == Role.STUDENT) {
			Presentation presentation = presentationDAO.findById(pid);

			List<Presentation> presentations = user.getPresentations();
			for (Presentation p : presentations) {
				if (presentation.getPid() == p.getPid()) {
					presentation.setPresentationStatus(presentationStatus);
					presentationDAO.save(presentation);

					ResponseStructure<PresentationStatus> rs = new ResponseStructure<PresentationStatus>(
							"Presentaion status updated to ", presentation.getPresentationStatus(), HttpStatus.OK);
					return new ResponseEntity<ResponseStructure<PresentationStatus>>(rs, HttpStatus.OK);
				}
			}
			return new ResponseEntity<String>("Unauthorized presentation access!", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>("Presentations are assigned to students only!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> saveTotalScore(Integer adminId, Integer pid, Double score) {
		User admin = userDAO.findById(adminId);

		if (admin.getRole() == Role.ADMIN) {
			Presentation presentation = presentationDAO.findById(pid);

			if (presentation.getPresentationStatus() == PresentationStatus.COMPLETED) {
				presentation.setUserTotalScore(score);
			} else {
				return new ResponseEntity<String>("Presentation is not completed!", HttpStatus.OK);
			}

			presentationDAO.save(presentation);
			ResponseStructure<Double> rs = new ResponseStructure<Double>("Presentation score upated to ",
					presentation.getUserTotalScore(), HttpStatus.OK);
			return new ResponseEntity<ResponseStructure<Double>>(rs, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Only Admin can assign score!", HttpStatus.BAD_REQUEST);
		}
	}
}
