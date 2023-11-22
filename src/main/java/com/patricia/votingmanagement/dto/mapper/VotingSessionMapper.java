package com.patricia.votingmanagement.dto.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.patricia.votingmanagement.dto.NewVotingSessionDTO;
import com.patricia.votingmanagement.dto.SessionResultDTO;
import com.patricia.votingmanagement.dto.VotingSessionDTO;
import com.patricia.votingmanagement.enums.ProposalStatusEnum;
import com.patricia.votingmanagement.model.VotingSession;

@Component
public class VotingSessionMapper {
	
	 public VotingSessionDTO toDTO(VotingSession entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new VotingSessionDTO(entity.getId(), entity.getSessionTime(), entity.getProposalId(), entity.getSessionStart());
	    }
	 
	 public NewVotingSessionDTO toNewVotingSessionDTO(VotingSession entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new NewVotingSessionDTO(entity.getSessionTime(), entity.getProposalId());
	    }
	 
	 public SessionResultDTO toSessionResultDTO(VotingSession entity, ProposalStatusEnum resultEnum) {
		 if (Objects.isNull(entity) || Objects.isNull(resultEnum)) {
	            return null;
	        }
	        return new SessionResultDTO(entity.getId(), entity.getSessionTime(), entity.getProposalId(), resultEnum.getDescription(), entity.getSessionStart(), entity.getSessionEnd());
	 }
}
