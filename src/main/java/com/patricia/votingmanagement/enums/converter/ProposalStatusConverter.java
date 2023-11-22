package com.patricia.votingmanagement.enums.converter;

import java.util.Objects;
import java.util.stream.Stream;

import com.patricia.votingmanagement.enums.ProposalStatusEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProposalStatusConverter implements AttributeConverter<ProposalStatusEnum, Integer>{

	@Override
	public Integer convertToDatabaseColumn(ProposalStatusEnum proposalStatus) {
		return Objects.isNull(proposalStatus) ? null : proposalStatus.getValue();
	}

	@Override
	public ProposalStatusEnum convertToEntityAttribute(Integer dbData) {
		//return null ;
		return Stream.of(ProposalStatusEnum.values())
				.filter(s -> s.getValue().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
