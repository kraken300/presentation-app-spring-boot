package com.pa.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pa.entity.Presentation;
import com.pa.entity.Rating;
import com.pa.enums.Role;
import com.pa.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

	private Integer id;

	private String name;

	private String email;

	private Long phone;

	private List<Presentation> presentations;

	private List<Rating> ratings;

	private Status status;

	private Role role;

	private Double userTotalScore;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
