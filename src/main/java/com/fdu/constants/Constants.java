package com.fdu.constants;

public enum Constants {

	//LABASSISTANT AND JOBAPPLICANT
	FIRSTNAME("firstName"),
	LASTNAME("lastName"),
	EMAIL("email"),
	PHONE("phone"),
	DATEAPPPLIED("dateApplied"),
	DATEHIRED("dateHired"),
	EDUCATION("education"),
	RESUME("resume"),
	STUDENTID("studentId"),
	
	//LABSCHEDULE
	LABSCHEDULEID("labScheduleId"),
	CAMPUS("campus"),
	LAB("lab"),
	STARTDATE("startDate"),
	ENDDATE("endDate"),
	STARTTIME("startTime"),
	ENDTIME("endTime"),
	ISRECURRING("isRecurring"),
	PROFESSORNAME("profName"),
	SUBJECT("subject"),
	
	//LABSCHEDULE
	ASSOCIATEID("associateId"),
	ASSOCIATENAME("associateName"),
	PROJECTS("projects"),
	ROLE("role");
	
	private String value;
	
	private Constants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
