package com.patricia.votingmanagement.dto.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.patricia.votingmanagement.dto.AssociateDTO;
import com.patricia.votingmanagement.dto.NewAssociateDTO;
import com.patricia.votingmanagement.model.Associate;

@Component
public class AssociateMapper {

	 public AssociateDTO toDTO(Associate entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new AssociateDTO(entity.getId(), entity.getName(), entity.getCpf());
	    }
	 
	 public NewAssociateDTO toNewProposalDTO(Associate entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new NewAssociateDTO(entity.getName(), entity.getCpf());
	    }
}
