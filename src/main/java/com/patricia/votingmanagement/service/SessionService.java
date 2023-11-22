package com.patricia.votingmanagement.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patricia.votingmanagement.dto.NewVoteDTO;
import com.patricia.votingmanagement.dto.VotingSessionDTO;
import com.patricia.votingmanagement.dto.mapper.VotingSessionMapper;
import com.patricia.votingmanagement.dto.NewVotingSessionDTO;
import com.patricia.votingmanagement.dto.SessionResultDTO;
import com.patricia.votingmanagement.enums.SessionStatusEnum;
import com.patricia.votingmanagement.enums.VoteValueEnum;
import com.patricia.votingmanagement.exception.InvalidRequestException;
import com.patricia.votingmanagement.exception.NotAuthorizedException;
import com.patricia.votingmanagement.exception.NotFoundException;
import com.patricia.votingmanagement.model.VotingSession;
import com.patricia.votingmanagement.repository.VotingSessionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionService {
	
	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	@Autowired
	private ProposalService proposalService;
	
	@Autowired 
	private AssociateService associateService;
	
	@Autowired 
	private VoteService voteService;
	
	@Autowired
	private VotingSessionMapper votingSessionMapper;
	
	@Transactional
	public VotingSessionDTO openNewSession(NewVotingSessionDTO newVotingSessionDTO){
		proposalService.validateProposalExists(newVotingSessionDTO.proposalId());
		
		VotingSession newSession = new VotingSession();
		
		newSession.setSessionTime(validateAndReturnSessionTime(newVotingSessionDTO.sessionTime()));
		newSession.setProposalId(newVotingSessionDTO.proposalId());	
		newSession.setStatus(SessionStatusEnum.IN_PROGRESS);
		newSession.setSessionStart(Timestamp.valueOf(LocalDateTime.now()));
		VotingSession session = votingSessionRepository.save(newSession);
		
		activateSessionTimer(session);
		
		return votingSessionMapper.toDTO(session);
	}
	
	private VotingSession validateVotingSessionExists(Long id) {
		java.util.Optional<VotingSession> session = votingSessionRepository.findById(id);
		if(session.isEmpty()) { 
			throw new NotFoundException(id, "Session");
		}
		if(!Objects.equals(session.get().getStatus(), SessionStatusEnum.IN_PROGRESS)) {
			throw new NotAuthorizedException("The session with id: "+session.get().getId()+" is not accepting votes. Status: " 
					+ SessionStatusEnum.SESSION_ENDED.getDescription());
	
		} 
		return session.get();
	}

	
	private Long validateAndReturnSessionTime(Long sessionTime) {
		if(Objects.isNull(sessionTime)) {
			return Long.valueOf(60);		
		} else if (Long.compare(sessionTime, Long.valueOf(0)) < 0 ) {
			throw new InvalidRequestException("Session time must be over 0 seconds.");
		} else return sessionTime;
	}
	
	private void validateVote(NewVoteDTO voteDTO) {
		VotingSession session = validateVotingSessionExists(voteDTO.sessionId());
		proposalService.validateProposalExists(session.getProposalId());
		voteService.checkIfAssociateAlreadyVoted(voteDTO.associateId(), voteDTO.sessionId());
		associateService.validateAssociateExists(voteDTO.associateId());
	}
	
	public void newVote (NewVoteDTO voteDTO) {
		validateVote(voteDTO);
		voteService.saveNewVote(voteDTO);
	}
	
	@Transactional
	private void endSession(VotingSession session) {
		session.setStatus(SessionStatusEnum.SESSION_ENDED);
		session.setSessionEnd(Timestamp.valueOf(LocalDateTime.now()));
		countVotes(session);
		votingSessionRepository.saveAndFlush(session);
	}
	
	private void activateSessionTimer(VotingSession session) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run () {
				endSession(session);
			}
		};
		timer.schedule(task, session.getSessionTime() * 1000);
	}

	private void countVotes(VotingSession session) {
		Long yesVotes = voteService.findAllBySessionId(session.getId())
				.stream()
				.filter(v -> v.getVoteValue() == VoteValueEnum.YES)
				.distinct()
				.count();
		
		Long noVotes = voteService.findAllBySessionId(session.getId())
				.stream()
				.filter(v -> v.getVoteValue() == VoteValueEnum.NO)
				.distinct()
				.count();	
		
		proposalService.updateProposalResults(session.getProposalId(), yesVotes, noVotes);
	}
	
	public SessionResultDTO getVotingResultsBySessionId(Long id) {
		java.util.Optional<VotingSession> session = votingSessionRepository.findById(id);
		if(session.isEmpty()) { 
			throw new NotFoundException(id, "Session");
		}
		VotingSession entity = session.get();
		if(!entity.getStatus().equals(SessionStatusEnum.SESSION_ENDED)) {
			throw new NotAuthorizedException("Error: cannot fetch result because session is stil ongoing.");
		}
		return votingSessionMapper.toSessionResultDTO(entity, proposalService.getProposalById(entity.getProposalId()).getStatus());
	}
	
}
