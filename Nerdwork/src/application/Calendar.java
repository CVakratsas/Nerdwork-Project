package application;

import java.util.ArrayList;

import application.Timeslot.Availability;

public class Calendar {
	
	/* Calendar class properties: */
	
	private ArrayList<Timeslot> timeslots;
	
	/*Calendar Constructor is here*/
	public Calendar() {
		timeslots = new ArrayList<>();
	}
	
	/* Calendar methods: */
	
	public void reserveTimeslot(Timeslot timeslot) {
		timeslot.setAvailability(Availability.RESERVED);
	}
	
	public void freeTimeslot(Timeslot timeslot) {
		timeslot.setStudent(null);
		timeslot.setAvailability(Availability.AVAILABLE);
	}
	
	public void addAvailableDate(String date) {
		timeslots.add(new Timeslot(date));
	}
		
	public void removeAvailableDate(Timeslot t) {
		timeslots.remove(t);
	}
	
	/* Calendar class Getters and Setters: */
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
}
