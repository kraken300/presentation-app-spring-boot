package com.pa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pa.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

}
