package com.pa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa.dao.UserDAO;

@Service
public class UserServiceImpl {

	@Autowired
	private UserDAO userDAO;

}
