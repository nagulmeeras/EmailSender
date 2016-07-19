package com.pramati.emailsender.beans;

import java.util.List;

public class TravelInfo {
	private String requestId;
	private String workLocation;
	private boolean isEmployee;
	private boolean isTicketChecked;
	private String billedTo;
	private Employee employee;
	private List<Traveller> travellers;
	private List<Trip> trips;
	private List<Facilities> facilites;
	private String clientName;
	private String approvingManagerEmail;
	
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public boolean isEmployee() {
		return isEmployee;
	}
	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	public boolean isTicketChecked() {
		return isTicketChecked;
	}
	public void setTicketChecked(boolean isTicketChecked) {
		this.isTicketChecked = isTicketChecked;
	}
	public String getBilledTo() {
		return billedTo;
	}
	public void setBilledTo(String billedTo) {
		this.billedTo = billedTo;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<Traveller> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<Traveller> travellers) {
		this.travellers = travellers;
	}
	public List<Trip> getTrips() {
		return trips;
	}
	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}
	public List<Facilities> getFacilites() {
		return facilites;
	}
	public void setFacilites(List<Facilities> facilites) {
		this.facilites = facilites;
	}
	public String getApprovingManagerEmail() {
		return approvingManagerEmail;
	}
	public void setApprovingManagerEmail(String approvingManagerEmail) {
		this.approvingManagerEmail = approvingManagerEmail;
	}
	
	
	
}
