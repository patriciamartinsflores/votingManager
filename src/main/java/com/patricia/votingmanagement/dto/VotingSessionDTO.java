package com.patricia.votingmanagement.dto;

import java.sql.Timestamp;

public record VotingSessionDTO(
		
		Long id, 		
		Long sessionTime,		
		Long proposalId, 
		Timestamp sessionStart) {

}
