package com.patricia.votingmanagement.dto;

import com.patricia.votingmanagement.enums.VoteValueEnum;
import com.patricia.votingmanagement.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewVoteDTO(
		@NotNull @Positive Long associateId,
		@NotNull @Positive Long sessionId,
		@NotNull @ValueOfEnum(enumClass = VoteValueEnum.class) VoteValueEnum voteValueEnum
		) {
			
}
