package com.patricia.votingmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewVoteDTO(
		@NotNull @Positive Long associateId,
		@NotNull @Positive Long sessionId,
		@NotNull int voteValue
		) {
			
}
