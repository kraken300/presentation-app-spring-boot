package com.pa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pa.entity.Presentation;
import com.pa.exception.PresentationNotFound;
import com.pa.repository.PresentationRepository;

@Repository
public class PresentationDAO {

	@Autowired
	private PresentationRepository presentationRepository;

	public Presentation save(Presentation presentation) {
		return presentationRepository.save(presentation);
	}

	public Presentation findById(Integer pid) {
		return presentationRepository.findById(pid)
				.orElseThrow(() -> new PresentationNotFound("Presentation not found!"));
	}

}
