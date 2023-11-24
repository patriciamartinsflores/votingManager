package com.patricia.votingmanagement.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.patricia.votingmanagement.dto.NewVoteDTO;
import com.patricia.votingmanagement.enums.VoteValueEnum;
import com.patricia.votingmanagement.model.Vote;
import com.patricia.votingmanagement.repository.VoteRepository;
import com.patricia.votingmanagement.exception.NotAuthorizedException;
import com.patricia.votingmanagement.exception.InvalidRequestException;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class VoteServiceTest {

	@InjectMocks
	VoteService voteService;
	
	@Mock
	VoteRepository voteRepository;
	
	Vote vote;
	NewVoteDTO voteDTO;
	
	@BeforeEach
	public void setUp() {
		vote = new Vote(Long.valueOf(1),Long.valueOf(1),VoteValueEnum.NO);
		voteDTO = new NewVoteDTO(vote.getAssociateId(), vote.getSessionId(), vote.getVoteValue().getValue());
	}
	
	@Test
	void mustSaveNewVote() {		
		voteRepository.save(vote);
		verify(voteRepository).save(vote);
		verifyNoMoreInteractions(voteRepository);
	}
	
	@Test
	void mustFindVotesBySessionId() {
		
		when(voteRepository.findAllBySessionId(vote.getSessionId())).thenReturn(Collections.singletonList(vote));

		List<Vote> votes = voteService.findAllBySessionId(vote.getSessionId());

		assertEquals(votes, Collections.singletonList(vote));
		
		verify(voteRepository).findAllBySessionId(vote.getSessionId());
		
		verifyNoMoreInteractions(voteRepository);
	}
	
	@Test
	void mustNotAllowMultipleVotesBySameAssociate() {
		when(voteRepository.existsBySessionIdAndAssociateId(vote.getSessionId(), vote.getAssociateId())).thenReturn(Boolean.TRUE);
		
		final NotAuthorizedException ex = assertThrows(NotAuthorizedException.class, () -> {
			voteService.checkIfAssociateAlreadyVoted(vote.getAssociateId(),vote.getSessionId());
		});
		
		assertThat(ex, notNullValue());
	}
	
	@Test
	void mustAllowNewVote() {
		when(voteRepository.existsBySessionIdAndAssociateId(vote.getSessionId(), vote.getAssociateId())).thenReturn(Boolean.FALSE);
		
		assertDoesNotThrow(() -> {
			voteService.checkIfAssociateAlreadyVoted(vote.getAssociateId(),vote.getSessionId());
		});
		
	}
	
	@Test
	void mustThrowExceptionWhenVoteIsNotYesOrNo() {
		final InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> {
			voteService.validateVoteValue(6);
		});
		assertThat(ex, notNullValue());
	}
	
	@Test
	void mustReturnEnumnWhenVoteIsYes() {
		assertDoesNotThrow(() -> {
			voteService.validateVoteValue(1);
		});
		assertEquals(VoteValueEnum.YES, voteService.validateVoteValue(1));
	}
	
	@Test
	void mustReturnEnumnWhenVoteIsNo() {
		assertDoesNotThrow(() -> {
			voteService.validateVoteValue(1);
		});
		assertEquals(VoteValueEnum.NO, voteService.validateVoteValue(0));
	}
	
}
