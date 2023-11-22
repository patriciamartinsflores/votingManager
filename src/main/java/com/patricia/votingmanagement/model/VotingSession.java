package com.patricia.votingmanagement.model;


import java.sql.Timestamp;

import com.patricia.votingmanagement.enums.SessionStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
public class VotingSession {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long sessionTime;//desired session duration in seconds
	
	@NotNull
	private Long proposalId;
	
	private SessionStatusEnum status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp sessionStart;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp sessionEnd;	
	
	public VotingSession() {
		super();
	}

	public Long getSessionTime() {
		return sessionTime;
	}

	public Long getId() {
		return id;
	}

	public void setSessionTime(Long sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public SessionStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SessionStatusEnum status) {
		this.status = status;
	}

	public Timestamp getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(Timestamp sessionStart) {
		this.sessionStart = sessionStart;
	}

	public Timestamp getSessionEnd() {
		return sessionEnd;
	}

	public void setSessionEnd(Timestamp sessionEnd) {
		this.sessionEnd = sessionEnd;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
