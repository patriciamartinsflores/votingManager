package com.patricia.votingmanagement.enums;

public enum VoteValueEnum {
	NO(0, "No"), 
	YES(1, "Yes");
	
	private int value;
	private String description;
	
	private VoteValueEnum (int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
}
