package com.pramati.emailsender.beans;

public class Employee {
	private String name;
	private String id;
	private String approvingManager;
	private String contactNumber;
	private String emailId;
	private String purposeOfTravel;
	private String clientName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApprovingManager() {
		return approvingManager;
	}

	public void setApprovingManager(String approvingManager) {
		this.approvingManager = approvingManager;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPurposeOfTravel() {
		return purposeOfTravel;
	}

	public void setPurposeOfTravel(String purposeOfTravel) {
		this.purposeOfTravel = purposeOfTravel;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
