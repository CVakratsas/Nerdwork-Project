/*
 * Class that contains information about the hours and dates
 * a Professor is available for an appointment with a Student
 * and also for the status of the Students' requests for appointments.
 */

package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Timeslot {
	
	private int id;
	private String studentId;
	private int professorId;
	private int dateTimestamp;
	private int day;
	private int startHour;
	private int endHour;
	private int status; //0 = Not Confirmed, 1 = Confirmed, 2 = Cancelled
	private String created_at;
	
	// Constructor used for available Timeslots
	public Timeslot(int dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}
	
	// Constructor used for Timeslots at when a Student requestsed an appointment
	public Timeslot(int id, String studentId, int professorId, int dateTimestamp, int status, String created_at) {
		this.id = id;
		this.studentId = studentId;
		this.professorId = professorId;
		this.dateTimestamp = dateTimestamp;
		this.status = status;
		this.created_at = created_at;
	}

	public int getId() {
		return id;
	}

	public String getStudentId() {
		return studentId;
	}

	public int getProfessorId() {
		return professorId;
	}

	public int getDateTimestamp() {
		return dateTimestamp;
	}

	public int getDay() {
		return day;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public int getStatus() {
		return status;
	}

	public String getCreated_at() {
		return created_at;
	}
	
}


