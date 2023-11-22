package com.patricia.votingmanagement.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;



import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import com.patricia.votingmanagement.model.Associate;
import com.patricia.votingmanagement.repository.AssociateRepository;
import com.patricia.votingmanagement.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AssociateServiceTest {
	
	@InjectMocks
	AssociateService associateService;
	
	@Mock
	AssociateRepository associateRepository;
	
	Associate associate;
	
	@BeforeEach
	public void setUp() {
		associate = new Associate();
}
	
	@Test
	void mustThrowExceptionWhenAssociateNotFound() {
		when(associateRepository.findById(associate.getId())).thenReturn(Optional.empty());
		final NotFoundException ex = assertThrows(NotFoundException.class, () -> {
			associateService.validateAssociateExists(associate.getId());
		});
		assertThat(ex, notNullValue());
		verify(associateRepository).findById(associate.getId());		
		verifyNoMoreInteractions(associateRepository);
	}
	
	@Test
	void mustNotThrowExceptionWhenAssociateExists() {
		when(associateRepository.findById(associate.getId())).thenReturn(Optional.of(associate));
		 assertDoesNotThrow(() -> {
			 associateService.validateAssociateExists(associate.getId());
		 });
		 verify(associateRepository).findById(associate.getId());		
		 verifyNoMoreInteractions(associateRepository);
	}
}
