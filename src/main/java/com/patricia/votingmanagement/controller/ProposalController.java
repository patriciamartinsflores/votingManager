package com.patricia.votingmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.patricia.votingmanagement.dto.NewProposalDTO;
import com.patricia.votingmanagement.dto.ProposalDTO;
import com.patricia.votingmanagement.service.ProposalService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/voting-system/proposal")
@Validated
public class ProposalController {
	
	@Autowired
	private ProposalService proposalService;
		
	@Operation(summary = "Create a new proposal")
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/new")
	public ProposalDTO registerNewProposal(@RequestBody @Valid NewProposalDTO newProposalDTO) {		
		return proposalService.newProposal(newProposalDTO);
	}
	
	@Operation(summary = "Returns list of all proposals")
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/get")
	public List<ProposalDTO> findAllProposals() {		
		return proposalService.findAllProposals();
	}
}
