/*
 * Class that contains information about the hours and dates
 * a Professor is available for an appointment with a Student
 * and also for the status of the Students' requests for appointments.
 */

package application.functinonality;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Timeslot {
	
	/* Timeslot class properties: */
	
	private HashMap<String, Integer> date;
	private Availability availability;
	private Student student;
	
	/* Timeslot constructor: */
	
	public Timeslot(HashMap<String, Integer> date) {
		this.date = date;
		this.availability = Availability.AVAILABLE;
	}
	
	/* Timeslot class Getters and Setters: */
	
	public HashMap<String, Integer> getDate(){
		return date;
	}
	
	public void setDate(HashMap<String, Integer> date) {
		this.date = date;
	}
	
	public void setAvailability(Availability availability) {
		this.availability = availability;
	}
	
	public Availability getAvailability() {
		return availability;
	}
	
	public void setStudent(Student student) {
		this.student = student;
		availability = Availability.PENDING;
	}
	
	public Student getStudent() {
		return student;
	}
	
	/* 
	 * Enumeration Class, that characterizes a timeslot as AVAILABLE
	 * (Student able to request appointment), PENDING (Professor, must
	 * deny or accept the appointment) and RESERVED (Professor has accepted
	 * an appointment with a Student at the certain timeslot).
	 */

	enum Availability {
		AVAILABLE,
		PENDING,
		RESERVED
	}
}


