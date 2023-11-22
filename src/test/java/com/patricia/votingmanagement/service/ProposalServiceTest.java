package com.patricia.votingmanagement.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import com.patricia.votingmanagement.model.Proposal;
import com.patricia.votingmanagement.repository.ProposalRepository;
import com.patricia.votingmanagement.dto.ProposalDTO;
import com.patricia.votingmanagement.dto.mapper.ProposalMapper;
import com.patricia.votingmanagement.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProposalServiceTest {

	@InjectMocks
	ProposalService proposalService;
	
	@Mock
	ProposalRepository proposalRepository;
	
	@Mock 
	ProposalMapper proposalMapper;
	
	Proposal proposal;
	ProposalDTO proposalDto;
	
	@BeforeEach
	public void setUp() {
		proposal = new Proposal("test proposal");
		proposalDto = new ProposalDTO(proposal.getId(), proposal.getDescription(), proposal.getStatus().getDescription());
	}
	
	@Test
	void mustThrowExceptionWhenProposalNotFound() {
		when(proposalRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());
		final NotFoundException ex = assertThrows(NotFoundException.class, () -> {
			proposalService.getProposalById(Long.valueOf(1));
		});
		assertThat(ex, notNullValue());
		verify(proposalRepository).findById(Long.valueOf(1));		
		verifyNoMoreInteractions(proposalRepository);
	}
	
	@Test
	void mustReturnProposalWhenFound() {
		when(proposalRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(proposal));
		assertDoesNotThrow(() -> {
			proposalService.getProposalById(Long.valueOf(1));
		 });
		verify(proposalRepository).findById(Long.valueOf(1));		
		verifyNoMoreInteractions(proposalRepository);
	}
	
	@Test
	void mustReturnListWhenThereAreProposals() {
		when(proposalRepository.findAll()).thenReturn(Collections.singletonList(proposal));
		when(proposalMapper.toDTO(proposal)).thenReturn(proposalDto);
		List<ProposalDTO> proposals = proposalService.findAllProposals();
		assertEquals(proposals, Collections.singletonList(proposalDto));
		verify(proposalRepository).findAll();
		verifyNoMoreInteractions(proposalRepository);
	}
}
