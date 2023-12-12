package com.patricia.votingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patricia.votingmanagement.model.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {

	List<Associate> findByCpf(String cpf);
	
}
