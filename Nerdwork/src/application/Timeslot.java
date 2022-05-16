/*
 * Class that contains information about the hours and dates
 * a Professor is available for an appointment with a Student
 * and also for the status of the Students' requests for appointments.
 */

package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timeslot {
	
	/* Timeslot class properties: */
	
	private Date date;
	private Availability availability;
	private Student student;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	
	/* Timeslot constructor: */
	
	public Timeslot(String date) {
		try {
			this.date = sdf.parse(date); // Parsing the String into a special Date type.
		}
		catch(ParseException pe){
			System.out.println("Parsing exception");
		}
		
		this.availability = Availability.AVAILABLE;
	}
	
	/* Timeslot class Getters and Setters: */
	
	public Date getDate() {
		return date;
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
	
	public SimpleDateFormat getDateFormat() {
		return sdf;
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


