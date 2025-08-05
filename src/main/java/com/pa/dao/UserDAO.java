package com.pa.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pa.entity.User;
import com.pa.repository.UserRepository;

@Repository
public class UserDAO {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public boolean findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password).isPresent();
	}

}
