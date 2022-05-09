package application;

import java.util.ArrayList;

public class Calendar {
	
	/* Calendar class properties: */
	
	private ArrayList<Timeslot> timeslots;
	
	/* Calendar methods: */
	
	public void addAvailableDate(Timeslot t) {
		timeslots.add(t);
	}
		
	public void removeAvailableDate(Timeslot t) {
		int tIndex;
		
		tIndex = timeslots.indexOf(t);
		timeslots.remove(tIndex);
	}
	
	/* Calendar class Getters and Setters: */
	
	public ArrayList<Timeslot> getTimeslots(){
		return timeslots;
	}
}
