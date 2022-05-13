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
			this.date = sdf.parse(date);
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
	
	public void setAvailability(Availability a) {
		this.availability = a;
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
	
	/* Enumeration Class: */

	enum Availability {
		AVAILABLE,
		PENDING,
		RESERVED
	}
}


