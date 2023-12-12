package com.patricia.votingmanagement.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "associate")
public class Associate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "associate_id")
	private Long id;
	
	@Column(name = "associate_name")
	@NotNull
	@Length(min = 5, max = 300)
	private String name;

	@Column(name = "associate_cpf", length = 11)
	@NotNull
	private String cpf;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Associate() {
		super();
	}

	public Associate(String name, String cpf) {
		super();
		this.cpf = cpf;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
