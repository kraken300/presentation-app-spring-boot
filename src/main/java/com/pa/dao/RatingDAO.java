package com.pa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pa.entity.Rating;
import com.pa.repository.RatingRepository;

@Repository
public class RatingDAO {

	@Autowired
	private RatingRepository ratingRepository;

	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

}
