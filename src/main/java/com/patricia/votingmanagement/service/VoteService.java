package com.patricia.votingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patricia.votingmanagement.dto.NewVoteDTO;
import com.patricia.votingmanagement.enums.VoteValueEnum;
import com.patricia.votingmanagement.exception.InvalidRequestException;
import com.patricia.votingmanagement.exception.NotAuthorizedException;
import com.patricia.votingmanagement.model.Vote;
import com.patricia.votingmanagement.repository.VoteRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	@Autowired	
	private VoteRepository voteRepository;
	
	
	@Transactional
	public void saveNewVote(NewVoteDTO voteDTO) {
		Vote vote = new Vote();
		vote.setSessionId(voteDTO.sessionId());
		vote.setAssociateId(voteDTO.associateId());
		vote.setVoteValue(validateVoteValue(voteDTO.voteValue()));
		voteRepository.save(vote);
		
	}
	
	public void checkIfAssociateAlreadyVoted(Long associateId, Long sessionId) {
		if(voteRepository.existsBySessionIdAndAssociateId(sessionId, associateId)) {
			throw new NotAuthorizedException("Error: only one vote allowed per associate.");
		}
	}	
	
	public List<Vote> findAllBySessionId(Long sessionId) {
		return voteRepository.findAllBySessionId(sessionId);
	}
	
	private VoteValueEnum validateVoteValue(int voteValue) {
		if (voteValue == VoteValueEnum.NO.getValue()) 
			return VoteValueEnum.NO;
		else if (voteValue == VoteValueEnum.YES.getValue()) 
			return VoteValueEnum.YES;
		else throw new InvalidRequestException("Error: vote values can only be YES or NO.");
	}
	
}
