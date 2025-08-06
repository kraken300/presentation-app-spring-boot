package com.pa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pa.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
