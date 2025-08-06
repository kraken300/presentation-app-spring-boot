package com.pa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFound userNotFound) {
		return new ResponseEntity<String>(userNotFound.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PresentationNotFound.class)
	public ResponseEntity<String> handlePresentationNotFound(PresentationNotFound presentationNotFound) {
		return new ResponseEntity<String>(presentationNotFound.getMessage(), HttpStatus.NOT_FOUND);
	}

}
