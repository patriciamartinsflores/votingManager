package com.patricia.votingmanagement.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;



import com.patricia.votingmanagement.repository.VotingSessionRepository;
import com.patricia.votingmanagement.enums.SessionStatusEnum;
import com.patricia.votingmanagement.exception.InvalidRequestException;
import com.patricia.votingmanagement.exception.NotFoundException;
import com.patricia.votingmanagement.model.VotingSession;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SessionServiceTest {

	@InjectMocks
	SessionService sessionService;
	
	@Mock
	VotingSessionRepository votingSessionRepository;
	
	VotingSession session;
	
	
	@BeforeEach
	public void setUp() {
		session = new VotingSession();
		session.setProposalId(Long.valueOf(1));
		session.setStatus(SessionStatusEnum.IN_PROGRESS);
		session.setSessionStart(Timestamp.valueOf(LocalDateTime.now()));
		session.setSessionTime(Long.valueOf(1));
	}
	
	
	@Test
	void mustThrowExceptionWhenSessionNotExists() {
		when(votingSessionRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());
		
		final NotFoundException ex = assertThrows(NotFoundException.class, () -> {
			sessionService.validateVotingSessionExists(Long.valueOf(1));
		});
		
		assertThat(ex, notNullValue());
	}
	
	@Test
	void mustReturnSessionWhenSessionExists() {
		when(votingSessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
		
		assertDoesNotThrow(() -> {
			sessionService.validateVotingSessionExists(session.getId());
		});

		assertEquals(session,sessionService.validateVotingSessionExists(session.getId()));
	}
	
	@Test
	void MustThrowExceptionWhenSessionTimeNegative() {
		final InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> {
			sessionService.validateAndReturnSessionTime(Long.valueOf(-1));
		});
		
		assertThat(ex, notNullValue());
	}
	
	@Test
	void MustThrowExceptionWhenSessionTimeZero() {
		final InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> {
			sessionService.validateAndReturnSessionTime(Long.valueOf(0));
		});
		
		assertThat(ex, notNullValue());
	}
	
	@Test
	void MustReturnDesiredSessionTime() {
		assertDoesNotThrow(() -> {
			sessionService.validateAndReturnSessionTime(Long.valueOf(1));
		});
		assertEquals(sessionService.validateAndReturnSessionTime(Long.valueOf(1)),Long.valueOf(1));
	}
	
	@Test
	void MustReturn60SecondsWhenTimeIsNotFilled() {
		assertDoesNotThrow(() -> {
			sessionService.validateAndReturnSessionTime(null);
		});
		assertEquals(sessionService.validateAndReturnSessionTime(null),Long.valueOf(60));
	}
}
