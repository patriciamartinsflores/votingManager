package com.patricia.votingmanagement.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;


public record NewProposalDTO (
		@NotNull
		@Length(min = 10, max = 300)
		String description) {

}
