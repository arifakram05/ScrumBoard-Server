package com.fdu.constants;

public enum Collections {
	
	LABSCHECULE("labschedule");

	private String value;
	
	private Collections(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
