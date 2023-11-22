package com.patricia.votingmanagement.enums;

public enum SessionStatusEnum {
	IN_PROGRESS(0, "Voting session in progress"),
	SESSION_ENDED(1, "Voting session is finished");
	
	private Integer value;
	private String description;
	
	private SessionStatusEnum (Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
