package com.patricia.votingmanagement.dto;

import java.sql.Timestamp;

public record SessionResultDTO(
		Long id,
		Long sessionTime,
		Long proposalId,
		String resultDescription,
		Timestamp sessionStart,
		Timestamp sessionEnd
		) {

}
