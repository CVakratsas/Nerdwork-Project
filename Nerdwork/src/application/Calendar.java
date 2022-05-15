/*
 * Class that contains the timeslots a Professor object set, as
 * AVAILABLE, RESERVED or PENDING
 */

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
	
	/*
	 * Method used to set a Timeslot as RESERVED for an appointment with a student.
	 * reserveTimeslot, receives a Timeslot class object (the timeslot at when
	 * the Professor will have an appointment with a Student), as a parameter
	 * and is a void type method.
	 */
	public void reserveTimeslot(Timeslot timeslot) {
		timeslot.setAvailability(Availability.RESERVED);
	}

	/*
	 * Method used to set a Timeslot as AVAILABLE for appointment requests.
	 * freeTimeslot, receives a Timeslot class object (the timeslot at when
	 * the Professor could not  have an appointment), as a parameter
	 * and is a void type method.
	 */
	public void freeTimeslot(Timeslot timeslot) {
		timeslot.setStudent(null);
		timeslot.setAvailability(Availability.AVAILABLE);
	}

	/*
	 * Method used to create a new Timeslot for appointment requests (marked AVAILABLE).
	 * addAvailableDate, receives a String class object (the date and hour in a special
	 * format("dd/mm/yyyy")), as a parameter and is a void type method.
	 */
	public void addAvailableDate(String date) {
		timeslots.add(new Timeslot(date));
	}
	
	/*
	 * Method used to delete a Timeslot for appointment requests (remove it from "this" Calendar).
	 * removeAvailableDate, receives a Timeslot class object (the timeslot to be deleted), 
	 * as a parameter and is a void type method.
	 */
	public void removeAvailableDate(Timeslot t) {
		timeslots.remove(t);
	}
	
	/* Calendar class Getters and Setters: */
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
}
