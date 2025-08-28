package com.pa.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pa.dao.PresentationDAO;
import com.pa.dao.RatingDAO;
import com.pa.dao.UserDAO;
import com.pa.dto.RatingRequestDTO;
import com.pa.entity.Presentation;
import com.pa.entity.Rating;
import com.pa.entity.User;
import com.pa.enums.PresentationStatus;
import com.pa.enums.Role;
import com.pa.rs.ResponseStructure;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PresentationDAO presentationDAO;

	@Autowired
	private RatingDAO ratingDAO;

	@Override
	public ResponseEntity<?> ratePresentation(Integer adminId, Integer studentId, Integer pid,
			RatingRequestDTO ratingRequestDTO) {
		User admin = userDAO.findById(adminId);

		Double totalScore = ((ratingRequestDTO.getCommunication() + ratingRequestDTO.getConfidence()
				+ ratingRequestDTO.getContent() + ratingRequestDTO.getInteraction() + ratingRequestDTO.getLiveliness()
				+ ratingRequestDTO.getUsageProps()) / 6);

		if (admin.getRole() == Role.ADMIN) {
			User student = userDAO.findById(studentId);
			Presentation presentation = presentationDAO.findById(pid);

			List<Presentation> allPresentations = student.getPresentations();
			Double sum = 0.0;
			for (Presentation presentation2 : allPresentations) {
				System.out.println(presentation2.getUserTotalScore());
				sum = sum + presentation2.getUserTotalScore();
			}

			System.out.println(sum / allPresentations.size());

			Rating rating = new Rating();
			BeanUtils.copyProperties(ratingRequestDTO, rating);

			if (presentation.getRating() != null) {
				return new ResponseEntity<String>(
						"Rating is already assigned to the presentation with id : " + presentation.getPid(),
						HttpStatus.BAD_REQUEST);
			}

			// TODO : Only assign rating to COMPLETED Presentation
			if (presentation.getPresentationStatus() == PresentationStatus.COMPLETED) {
				rating.setTotalScore(totalScore);
				rating.setUser(student);
				// presentation.setRating(rating);
				rating.setPresentation(presentation);
				presentation.setUserTotalScore(totalScore);

				// TODO: Assign total score to user by calculating the average of all
				// presentations score
				student.setUserTotalScore(sum / allPresentations.size());

				Rating saved = ratingDAO.save(rating);

				ResponseStructure<Rating> rs = new ResponseStructure<Rating>(
						"Rating assigned to presentation with topic : " + presentation.getTopic(), saved,
						HttpStatus.OK);
				return new ResponseEntity<ResponseStructure<Rating>>(rs, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(
						"Rating can only be assigned to the completed presentation. Current presentation status : "
								+ presentation.getPresentationStatus(),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<String>("Rating can be assigned by admin only!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> getRatingByPresentationId(Integer pid) {
		Presentation presentation = presentationDAO.findById(pid);
		Rating rating = presentation.getRating();
		ResponseStructure<Rating> rs = new ResponseStructure<Rating>("Rating fetched successfully!", rating,
				HttpStatus.OK);
		return new ResponseEntity<ResponseStructure<Rating>>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOverallRatingByStudentId(Integer id) {
		User student = userDAO.findById(id);
		Double userTotalScore = student.getUserTotalScore();
		ResponseStructure<Double> rs = new ResponseStructure<Double>(
				"Overall presentation rating fetched successfully for student : " + student.getName(), userTotalScore,
				HttpStatus.OK);
		return new ResponseEntity<ResponseStructure<Double>>(rs, HttpStatus.OK);
	}
}
