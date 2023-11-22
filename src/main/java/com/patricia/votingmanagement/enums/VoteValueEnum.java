package com.patricia.votingmanagement.enums;

public enum VoteValueEnum {
	NO(0), 
	YES(1);
	
	private int value;
	
	private VoteValueEnum (int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}	
	
}
