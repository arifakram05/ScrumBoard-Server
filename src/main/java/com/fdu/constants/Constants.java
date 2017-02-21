package com.fdu.constants;

public enum Constants {

	//patterns
	NUMBERS_ONLY("[0-9]+"),
	
	//collections
	ASSOCIATES("associates"),
	PROJECTS("projects"),
	
	//fields in associates
	ASSOCIATEID("associateId"),
	ASSOCIATENAME("associateName"),
	ROLE("role"),
	
	//fields in projects
	PROJECTNAME("projectName"),
	PROJECTID("projectId"),
	
	//fields
	ACTUALDATE("actualDate"),
	SCRUMDETAILS("scrumDetails"),
	STARTDATE("startDate"),
	ENDDATE("endDate"),
	SCRUMNAME("scrumName"),
	YESTERDAY("yesterday"),
	TODAY("today"),
	ROADBLOCKS("roadblocks"),
	
	//validatons
	SCRIPTTAGS("<script>(.*?)</script>"),
	JAVASCRIPT("javascript:");
	
	private String value;
	
	private Constants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
