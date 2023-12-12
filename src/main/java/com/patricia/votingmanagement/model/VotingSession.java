package com.patricia.votingmanagement.model;


import java.sql.Timestamp;

import com.patricia.votingmanagement.enums.SessionStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "voting_session")
public class VotingSession {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "session_id")
	private Long id;
	
	@Column(name = "session_time")
	private Long sessionTime;//desired session duration in seconds
	
	@NotNull
	@Column(name = "proposal_id")
	private Long proposalId;
	
	@Column(name = "session_status")
	private SessionStatusEnum status;
	
	@Column(name = "session_start")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp sessionStart;	
	
	@Column(name = "session_end")
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
