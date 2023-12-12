package com.patricia.votingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patricia.votingmanagement.dto.AssociateDTO;
import com.patricia.votingmanagement.dto.NewAssociateDTO;
import com.patricia.votingmanagement.dto.mapper.AssociateMapper;
import com.patricia.votingmanagement.exception.InvalidRequestException;
import com.patricia.votingmanagement.exception.NotFoundException;
import com.patricia.votingmanagement.model.Associate;
import com.patricia.votingmanagement.repository.AssociateRepository;

import jakarta.transaction.Transactional;


@Service
public class AssociateService {

	@Autowired
	private AssociateRepository associateRepository;
	
	@Autowired
	private AssociateMapper associateMapper;
	
	public void validateAssociateExists(Long id) {
		if(associateRepository.findById(id).isEmpty()) { 
			throw new NotFoundException(id, "Associate");
		}
	}
	
	@Transactional
	public AssociateDTO newAssociate(NewAssociateDTO newAssociateDTO) {
		validateAssociateNotExistsByCPF(newAssociateDTO.cpf());
		Associate entity = new Associate(newAssociateDTO.name(), newAssociateDTO.cpf());
		return associateMapper.toDTO(associateRepository.save(entity));
	}
	
	public void validateAssociateNotExistsByCPF(String cpf) {
		if(!associateRepository.findByCpf(cpf).isEmpty()) { 
			throw new InvalidRequestException("Error: associate with CPF "+cpf+" already exists in the database.");
		}
	}
}
