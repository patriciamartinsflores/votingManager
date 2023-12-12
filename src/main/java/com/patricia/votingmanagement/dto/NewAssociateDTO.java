package com.patricia.votingmanagement.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record NewAssociateDTO(
		@Column(name = "associate_name") @NotNull @Length(min = 5, max = 300)
		String name,
		@Column(name = "associate_cpf", length = 11) @NotNull
		String cpf
		) {

}
