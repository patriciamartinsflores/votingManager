package com.patricia.votingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Voting Manager API", version = "1", description = "API for creating and managing vote sessions"))
public class VotingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingManagementApplication.class, args);
	}

}
