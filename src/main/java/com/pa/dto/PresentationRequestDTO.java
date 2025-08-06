package com.pa.dto;

import com.pa.enums.PresentationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresentationRequestDTO {

	private String course;

	private String topic;

	@NotNull(message = "Presentation Status should not be empty")
	private PresentationStatus presentationStatus = PresentationStatus.ASSIGNED;

	private Double userTotalScore = 0.0;
}
