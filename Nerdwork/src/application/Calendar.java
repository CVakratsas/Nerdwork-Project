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
	
	public void addAppointment(Student student, Timeslot timeslot) {
		int pIndex;
		
		pIndex = timeslots.indexOf(timeslot);
		timeslots.get(pIndex).setStudent(student, Availability.AVAILABLE);
	}
	
	public void addAvailableDate(Timeslot t) {
		timeslots.add(t);
	}
		
	public void removeAvailableDate(Timeslot t) {
		int tIndex;
		tIndex = timeslots.indexOf(t);
		
		if(tIndex!=-1) {
			timeslots.remove(tIndex);
		}
	}
	
	/* Calendar class Getters and Setters: */
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
}
