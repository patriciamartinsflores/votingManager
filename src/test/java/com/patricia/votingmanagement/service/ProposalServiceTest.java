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
import com.patricia.votingmanagement.dto.NewProposalDTO;
import com.patricia.votingmanagement.dto.ProposalDTO;
import com.patricia.votingmanagement.dto.mapper.ProposalMapper;
import com.patricia.votingmanagement.enums.ProposalStatusEnum;
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
	NewProposalDTO newProposalDTO;
	
	@BeforeEach
	public void setUp() {
		proposal = new Proposal("test proposal");
		proposalDto = new ProposalDTO(proposal.getId(), proposal.getDescription(), proposal.getStatus().getDescription());
		newProposalDTO = new NewProposalDTO(proposal.getDescription());
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
	
	@Test
	void mustSetProposalAsAcceptedIfMoreYesVotes() {
		when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
		proposalService.updateProposalResults(proposal.getId(), Long.valueOf(2), Long.valueOf(1));
		verify(proposalRepository).findById(proposal.getId());
		assertEquals(ProposalStatusEnum.PROPOSAL_ACCEPTED,proposal.getStatus());
		verify(proposalRepository).saveAndFlush(proposal);
	}
	
	@Test
	void mustSetProposalAsRejectedIfMoreNoVotes() {
		when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
		proposalService.updateProposalResults(proposal.getId(), Long.valueOf(1), Long.valueOf(2));
		verify(proposalRepository).findById(proposal.getId());
		assertEquals(ProposalStatusEnum.PROPOSAL_REJECTED,proposal.getStatus());
		verify(proposalRepository).saveAndFlush(proposal);
	}
	
	@Test
	void mustSetProposalAsTieIfEqualNumberOfYesAndNoVotes() {
		when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
		proposalService.updateProposalResults(proposal.getId(), Long.valueOf(0), Long.valueOf(0));
		verify(proposalRepository).findById(proposal.getId());
		assertEquals(ProposalStatusEnum.TIE,proposal.getStatus());
		verify(proposalRepository).saveAndFlush(proposal);
	}
	
	@Test
	void mustNotThrowExceptionWhenIdValidatedDoesExist() {
		when(proposalRepository.existsById(proposal.getId())).thenReturn(true);
		assertDoesNotThrow(() -> {
			proposalService.validateProposalById(proposal.getId());
		 });
		verify(proposalRepository).existsById(proposal.getId());
		verifyNoMoreInteractions(proposalRepository);
	}
	
	@Test
	void mustThrowExceptionWhenIdValidatedDoesNotExist() {
		when(proposalRepository.existsById(proposal.getId())).thenReturn(false);
		final NotFoundException ex = assertThrows(NotFoundException.class, () -> {
			proposalService.validateProposalById(proposal.getId());
		});
		assertThat(ex, notNullValue());
		verify(proposalRepository).existsById(proposal.getId());
		verifyNoMoreInteractions(proposalRepository);
	}
	
	@Test
	void mustCreateNewProposalWithNoErros() {
		assertDoesNotThrow(() -> {
			proposalService.newProposal(newProposalDTO);
		 });
	}
}
