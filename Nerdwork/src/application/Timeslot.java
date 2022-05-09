package application;

import java.util.*;
import java.text.*;

public class Timeslot {
	
	/* Timeslot class properties: */
	
	private Date date;
	private Availability availability;
	private Student student;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	
	/* Timeslot constructor: */
	
	public Timeslot(String date) {
		try {
			this.date = sdf.parse(date);
		}
		catch(ParseException pe){
			System.out.println("Parsing exception");
		}
	}
	
	/* Timeslot methods: */
	
	public void setStudent(Student student, Availability availability) {
		this.student = student;
		this.availability = availability;
	}
	
	public void clearTimeslot() {
		date = null;
	}
	
	/* Timeslot class Getters and Setters: */
	
	public Date getDate() {
		return date;
	}
	
	public Availability getAvailability() {
		return availability;
	}
	
	public Student getStudent() {
		return student;
	}
	
	/* Enumeration Class: */

	enum Availability{
		AVAILABLE,
		PENDING,
		RESERVED
	}
}


