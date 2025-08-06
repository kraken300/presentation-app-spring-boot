package com.pa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDTO {

	private Double communication;

	private Double confidence;

	private Double content;

	private Double interaction;

	private Double liveliness;

	private Double usageProps;

}
