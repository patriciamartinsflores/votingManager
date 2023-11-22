package com.patricia.votingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patricia.votingmanagement.model.VotingSession;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

}
