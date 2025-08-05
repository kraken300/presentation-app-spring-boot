package com.pa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pa.repository.UserRepository;

@Repository
public class UserDAO {

	@Autowired
	private UserRepository userRepository;

}
