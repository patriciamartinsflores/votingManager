package com.patricia.votingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.patricia.votingmanagement.dto.NewVoteDTO;
import com.patricia.votingmanagement.dto.NewVotingSessionDTO;
import com.patricia.votingmanagement.dto.SessionResultDTO;
import com.patricia.votingmanagement.dto.VotingSessionDTO;
import com.patricia.votingmanagement.service.SessionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



@RestController
@RequestMapping("/api/voting-system/session")
@Validated
public class VotingSessionController {

	@Autowired
	private SessionService sessionService;
	
	
	@Operation(summary = "Initiate a voting session")
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/new")
	public VotingSessionDTO openNewSession(@RequestBody @Valid NewVotingSessionDTO newVotingSessionDTO) {		
		return sessionService.openNewSession(newVotingSessionDTO);
	}
	
	@Operation(summary = "Receives an associate's vote")
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/vote")
	public void newVote(@RequestBody @Valid NewVoteDTO newVoteDTO) {
		sessionService.newVote(newVoteDTO);
	}
	
	@Operation(summary = "Returns the result of a voting session")
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/get-result/{id}")
	public SessionResultDTO getVotingResultsBySessionId(@PathVariable @NotNull @Positive Long id) {
		return sessionService.getVotingResultsBySessionId(id);
	}
		
}
