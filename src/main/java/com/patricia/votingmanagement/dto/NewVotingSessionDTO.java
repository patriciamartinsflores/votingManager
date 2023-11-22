package com.patricia.votingmanagement.dto;


import jakarta.validation.constraints.NotNull;

public record NewVotingSessionDTO (		
		@NotNull
		Long proposalId,
		@NotNull
		Long sessionTime
		) {

}
