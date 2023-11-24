package com.patricia.votingmanagement.model;

import com.patricia.votingmanagement.enums.VoteValueEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


@Entity
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	
	@NotNull
	private Long associateId;
	
	@NotNull
	private Long sessionId;
	
	@NotNull 
	private VoteValueEnum voteValue;
	
	public Vote() {
		super();
	}

	public Vote(Long associateId, Long sessionId, VoteValueEnum value) {
		super();
		this.associateId = associateId;
		this.sessionId = sessionId;
		this.voteValue = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public VoteValueEnum getVoteValue() {
		return voteValue;
	}

	public void setVoteValue(VoteValueEnum voteValue) {
		this.voteValue = voteValue;
	}
	
	

}
