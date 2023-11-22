package com.patricia.votingmanagement.dto.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.patricia.votingmanagement.dto.NewProposalDTO;
import com.patricia.votingmanagement.dto.ProposalDTO;
import com.patricia.votingmanagement.model.Proposal;

@Component
public class ProposalMapper {
	
	 public ProposalDTO toDTO(Proposal entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new ProposalDTO(entity.getId(), entity.getDescription(), entity.getStatus().getDescription());
	    }
	 
	 public NewProposalDTO toNewProposalDTO(Proposal entity) {
	        if (Objects.isNull(entity)) {
	            return null;
	        }
	        return new NewProposalDTO(entity.getDescription());
	    }

}
