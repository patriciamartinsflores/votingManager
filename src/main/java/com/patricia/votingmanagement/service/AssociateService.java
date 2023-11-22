package com.patricia.votingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patricia.votingmanagement.exception.NotFoundException;
import com.patricia.votingmanagement.repository.AssociateRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssociateService {

	@Autowired
	private AssociateRepository associateRepository;
	
	public void validateAssociateExists(Long id) {
		if(associateRepository.findById(id).isEmpty()) { 
			throw new NotFoundException(id, "Associate");
		}
	}
	
	
}
