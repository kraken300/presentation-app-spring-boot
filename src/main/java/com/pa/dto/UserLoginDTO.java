package com.pa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
	@Email(message = "Enter a valid email")
	private String email;

	@Size(min = 5, message = "Password must be of 5 or more than 5 letters")
	private String password;
}
