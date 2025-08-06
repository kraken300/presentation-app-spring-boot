package com.pa.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pa.enums.Role;
import com.pa.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_info")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Email(message = "Enter a valid email")
	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private Long phone;

	@Size(min = 5, message = "Password must be of 5 or more than 5 letters")
	private String password;

	@OneToMany(mappedBy = "user")
	private List<Presentation> presentations;

	@OneToMany(mappedBy = "user")
	private List<Rating> ratings;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role = Role.STUDENT;

	private Double userTotalScore;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
