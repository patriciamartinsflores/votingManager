package com.patricia.votingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patricia.votingmanagement.model.Proposal;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

}