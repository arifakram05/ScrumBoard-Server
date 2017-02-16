package com.arif.db;

public class ComputingServicesDBOperations  {

	/*@Override
	public boolean saveJobApplicant(JobApplicant jobApplicantDetails) {
		//connect to database
		MongoCollection<Document> collection = DBConnection.INSTANCE.establishDBConnection().getCollection("jobapplicants");

		Document document = new Document();		
				
		document.append(Constants.FIRSTNAME.getValue(), jobApplicantDetails.getFirstName());
		document.append(Constants.LASTNAME.getValue(), jobApplicantDetails.getLastName());
		document.append(Constants.DATEAPPPLIED.getValue(), new Date());		
		document.append(Constants.EMAIL.getValue(), jobApplicantDetails.getEmail());
		document.append(Constants.PHONE.getValue(), jobApplicantDetails.getPhone());
		document.append(Constants.RESUME.getValue(), jobApplicantDetails.getResume());
		document.append(Constants.STUDENTID.getValue(), jobApplicantDetails.getStudentId());
		document.append(Constants.EDUCATION.getValue(), jobApplicantDetails.getEducation());
		//insert details into database
		collection.insertOne(document);
	
		return true;
	}

	@Override
	public List<JobApplicant> getAllJobApplicants() {
		List<JobApplicant> allJobApplicants = new ArrayList<>();
		//connect to database
		MongoCollection<Document> collection = DBConnection.INSTANCE.establishDBConnection().getCollection("jobapplicants");
		
		Block<Document> processRetreivedData = (document) -> {
			//ObjectMapper is unable to map Date field, this is a bug from MongoDB side
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule mongoDateModule =
	                new SimpleModule("MongoDateDeserializer", new Version(1, 0, 0, ""));
	        mongoDateModule.addDeserializer(Date.class, new DateDeserializer());
			mapper.registerModule(mongoDateModule);			
			JobApplicant jobApplicant = mapper.readValue(document.toJson(), JobApplicant.class);
			allJobApplicants.add(jobApplicant);
			String jsonString = JSON.serialize(document);
			
			JobApplicant jobApplicant = new JobApplicant();
			
			jobApplicant.setFirstName(document.get(Constants.FIRSTNAME.getValue()).toString());
			jobApplicant.setLastName(document.get(Constants.LASTNAME.getValue()).toString());
			jobApplicant.setEmail(document.get(Constants.EMAIL.getValue()).toString());
			jobApplicant.setPhone(document.get(Constants.PHONE.getValue()).toString());
			jobApplicant.setEducation(document.get(Constants.EDUCATION.getValue()).toString());
			jobApplicant.setStudentId(Integer.valueOf(document.get(Constants.STUDENTID.getValue()).toString()));			
			//jobApplicant.setResume(document.get(Constants.RESUME.getValue()));
			jobApplicant.setDateApplied((Date)document.get(Constants.DATEAPPPLIED.getValue()));

			allJobApplicants.add(jobApplicant);
			
			System.out.println(document.toJson());
		};
		
		//get all records
		collection.find().projection(fields(excludeId())).sort(ascending(Constants.DATEAPPPLIED.getValue())).forEach(processRetreivedData);
		
		return allJobApplicants;
	}

	@Override
	public boolean saveLabAssistant(LabAssistant labAssistant) {
		//connect to database
		MongoCollection<Document> collection = DBConnection.INSTANCE.establishDBConnection().getCollection("labassistants");

		Document document = new Document();		
				
		document.append(Constants.FIRSTNAME.getValue(), labAssistant.getFirstName());
		document.append(Constants.LASTNAME.getValue(), labAssistant.getLastName());
		document.append(Constants.DATEAPPPLIED.getValue(), labAssistant.getDateApplied());
		document.append(Constants.DATEHIRED.getValue(), new Date());
		document.append(Constants.EMAIL.getValue(), labAssistant.getEmail());
		document.append(Constants.PHONE.getValue(), labAssistant.getPhone());
		document.append(Constants.RESUME.getValue(), labAssistant.getResume());
		document.append(Constants.STUDENTID.getValue(), labAssistant.getStudentId());
		document.append(Constants.EDUCATION.getValue(), labAssistant.getEducation());
		
		//insert details into database
		collection.insertOne(document);
	
		return true;
	}

	@Override
	public boolean deleteJobApplicant(Integer studentId) {
		MongoCollection<Document> collection = DBConnection.INSTANCE.establishDBConnection().getCollection("jobapplicants");
		DeleteResult result = collection.deleteOne(eq("studentId", studentId));
		return result.wasAcknowledged();
	}

	@Override
	public boolean saveLabSchedule(LabSchedule labSchedule) {
		//connect to database
		MongoCollection<Document> collection = DBConnection.INSTANCE.establishDBConnection().getCollection(Collections.LABSCHECULE.getValue());
		
		Document document = new Document();
				
		document.append(Constants.LABSCHEDULEID.getValue(), labSchedule.getLabScheduleId());
		document.append(Constants.CAMPUS.getValue(), labSchedule.getCampus());
		document.append(Constants.LAB.getValue(), labSchedule.getLab());
		document.append(Constants.STARTDATE.getValue(), labSchedule.getStartDate());
		document.append(Constants.ENDDATE.getValue(), labSchedule.getEndDate());
		document.append(Constants.STARTTIME.getValue(), labSchedule.getStartTime());
		document.append(Constants.ENDTIME.getValue(), labSchedule.getEndTime());
		document.append(Constants.ISRECURRING.getValue(), labSchedule.isRecurring());
		document.append(Constants.PROFESSORNAME.getValue(), labSchedule.getProfName());
		document.append(Constants.SUBJECT.getValue(), labSchedule.getSubject());
		
		//insert details into database
		collection.insertOne(document);
		
		return true;
	}*/

}
