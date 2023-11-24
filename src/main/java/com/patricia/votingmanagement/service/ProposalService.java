package com.patricia.votingmanagement.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patricia.votingmanagement.dto.NewProposalDTO;
import com.patricia.votingmanagement.dto.ProposalDTO;
import com.patricia.votingmanagement.dto.mapper.ProposalMapper;
import com.patricia.votingmanagement.enums.ProposalStatusEnum;
import com.patricia.votingmanagement.exception.NotFoundException;
import com.patricia.votingmanagement.model.Proposal;
import com.patricia.votingmanagement.repository.ProposalRepository;

import jakarta.transaction.Transactional;

@Service
public class ProposalService {

	@Autowired	
	private ProposalRepository proposalRepository;
	
	@Autowired
	private ProposalMapper proposalMapper;
	
	@Transactional
	public ProposalDTO newProposal(NewProposalDTO newProposalDTO) {
		Proposal proposalEntity = new Proposal(newProposalDTO.description());
		return proposalMapper.toDTO(proposalRepository.save(proposalEntity));
	}
	
	public List<ProposalDTO> findAllProposals() {
		return proposalRepository.findAll()
		.stream()
		.map(proposalMapper::toDTO)
		.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public Proposal getProposalById(Long id) {
		return proposalRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Proposal"));
	}
	
	public void validateProposalById(Long id) {
		if (!proposalRepository.existsById(id))
			throw new NotFoundException(id, "Proposal");
	}
	
	@Transactional
	public void updateProposalResults(Long proposalId, Long yesVotes, Long noVotes) {
		Proposal proposal = proposalRepository.findById(proposalId).orElseThrow();
		if (yesVotes.compareTo(noVotes) == 0) {
			proposal.setStatus(ProposalStatusEnum.TIE);
		} else if (yesVotes.compareTo(noVotes) > 0) {
			proposal.setStatus(ProposalStatusEnum.PROPOSAL_ACCEPTED);
		}
		else {
			proposal.setStatus(ProposalStatusEnum.PROPOSAL_REJECTED);
		} 
		proposalRepository.saveAndFlush(proposal);
	}
}
