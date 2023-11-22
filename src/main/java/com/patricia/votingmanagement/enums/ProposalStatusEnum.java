package com.patricia.votingmanagement.enums;

public enum ProposalStatusEnum {
	PROPOSAL_CREATED(0, "Proposal has just been created"),
	PROPOSAL_ACCEPTED(1, "Proposal approved by the majority of voters"), 
	PROPOSAL_REJECTED(2, "Proposal rejected by the majority of voters"),
	TIE(3, "Equal number of YES and NO votes");
	
	private Integer value;
	private String description;
	
	private ProposalStatusEnum (Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
