package com.traveldesk.emailservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.traveldesk.emailservice.beans.Employee;
import com.traveldesk.emailservice.beans.Facilities;
import com.traveldesk.emailservice.beans.TravelInfo;
import com.traveldesk.emailservice.beans.Traveller;
import com.traveldesk.emailservice.beans.Trip;

@Component
public class DataParser {

	public TravelInfo parseRequestData(HashMap<String, String> inputData) {
		TravelInfo travelInfo = new TravelInfo();
		travelInfo.setRequestId("12603");
		travelInfo.setWorkLocation("Hyderabad");
		travelInfo.setEmployee(true);
		travelInfo.setBilledTo("employee");
		travelInfo.setTicketChecked(true);
		travelInfo.setApprovingManagerEmail("nagulmeera.shaik@gmail.com");

		Traveller traveller = new Traveller();
		traveller.setdOB("05-03-1991");
		traveller.setGender("Male");
		traveller.setMealPreference("Lunch");
		traveller.setName("Nagulmeera");
		traveller.setRelationship("");

		Trip trip = new Trip();
		trip.setType("Visit");
		trip.setFrom("Hyderabad");
		trip.setTo("Delhi");
		trip.setDateOfJourney("14-07-2016");
		trip.setPreferredTime("12:00");

		Trip trip1 = new Trip();
		trip1.setType("Visit");
		trip1.setFrom("Hyderabad");
		trip1.setTo("Delhi");
		trip1.setDateOfJourney("14-07-2016");
		trip1.setPreferredTime("12:00");

		List<Trip> trips = new ArrayList<Trip>();
		trips.add(trip);
		trips.add(trip1);

		Traveller traveller1 = new Traveller();
		traveller1.setName("Nagulmeera Shaik");
		traveller1.setGender("Male");
		traveller1.setdOB("05-03-1991");
		traveller1.setRelationship("");
		traveller1.setMealPreference("Dinner");

		List<Traveller> travellersDetails = new ArrayList<Traveller>();
		travellersDetails.add(traveller);
		travellersDetails.add(traveller1);

		Employee employee = new Employee();
		employee.setApprovingManager("afasfasfasf");
		employee.setName("Nagulmeera");
		employee.setContactNumber("afasfasfas");
		employee.setId("12603");
		employee.setEmailId("sknagulmeera06@gmail.com");
		employee.setClientName("Gallagher");
		employee.setPurposeOfTravel("Vocation");

		Facilities facilities = new Facilities();
		facilities.setHotel(true);
		facilities.setTicket(true);
		facilities.setForex(false);
		facilities.setInsurance(false);
		facilities.setSimCard(true);
		facilities.setVisa(false);

		List<Facilities> facilitiesList = new ArrayList<Facilities>();
		facilitiesList.add(facilities);

		travelInfo.setTravellers(travellersDetails);
		travelInfo.setTrips(trips);
		travelInfo.setEmployee(employee);
		travelInfo.setFacilites(facilitiesList);

		return travelInfo;
	}
}
