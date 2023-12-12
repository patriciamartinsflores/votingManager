package com.patricia.votingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.patricia.votingmanagement.dto.AssociateDTO;
import com.patricia.votingmanagement.dto.NewAssociateDTO;
import com.patricia.votingmanagement.service.AssociateService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/voting-system/associate")
@Validated
public class AssociateController {
	
	@Autowired
	private AssociateService associateService;
	
	@Operation(summary = "Save a new associate")
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/new")
	public AssociateDTO registerNewProposal(@RequestBody @Valid NewAssociateDTO newAssociateDTO) {		
		return associateService.newAssociate(newAssociateDTO);
	}
}
