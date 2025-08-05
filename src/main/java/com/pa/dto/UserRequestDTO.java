package com.pa.dto;

import com.pa.enums.Role;
import com.pa.enums.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
	private String name;

	@Email(message = "Enter a valid email")
	private String email;

	private Long phone;

	@Size(min = 5, message = "Password must be of 5 or more than 5 letters")
	private String password;

	@NotNull(message = "Status should not be null")
	private Status status = Status.ACTIVE;

	@NotNull(message = "Role should not be null")
	private Role role = Role.STUDENT;

}
