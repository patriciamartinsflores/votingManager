package com.patricia.votingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patricia.votingmanagement.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	boolean existsBySessionIdAndAssociateId(Long sessionId, Long associateId);
	
	List<Vote> findAllBySessionId(Long sessionId);
	
}