/*
 * Class that contains information for appointments of a student
 * with a professor and their status. Note that appointments are
 * stored as starting date and ending date seconds since 1st January
 * 1970 00:00:00. Timeslot also contains methods that can be used to 
 * more easily parse these data into more usable forms.
 */

package application.functionality;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Timeslot {
	
	public static final String Days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}; 
	public static final String Months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "December"};
	private int id;
	private String studentId;
	private int professorId;
	private int startHourTimestamp; // Seconds since 1st January 1970 00:00:00 for startHour
	private int endHourTimestamp; // // Seconds since 1st January 1970 00:00:00 for endHour
	private int status; //0 = Not Confirmed, 1 = Confirmed, 2 = Cancelled
	private String created_at;
	
	// Constructor used for available Timeslots
	public Timeslot(int startHourTimestamp, int endHourTimestamp) {
		this.startHourTimestamp = startHourTimestamp;
		this.endHourTimestamp = endHourTimestamp;
		status = -1;
	}
	
	// Constructor used for Timeslots when a Student requested an appointment
	public Timeslot(int id, String studentId, int professorId, int startHourTimestamp, int endHourTimestamp, int status, String created_at) {
		this.id = id;
		this.studentId = studentId;
		this.professorId = professorId;
		this.startHourTimestamp = startHourTimestamp;
		this.endHourTimestamp = endHourTimestamp;
		this.status = status;
		this.created_at = created_at;
	}
	
	/*
	 * Method used to check if an appointment is outdated, using the GMT
	 * timezone.
	 * It returns false if the appointment can still be considered active
	 * and (available, requested, reserved) and true otherwise.
	 */
	public boolean checkOutdated() {
		Calendar day = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date dayTimestamp = day.getTime();
		boolean outdated = false;
		
		// A requested or available, can be considered outdated when current time exceeds their starting hour.
		if (((int)(dayTimestamp.getTime() / 1000)) > startHourTimestamp && status != 1)
			outdated = true;
		
		// A reserved appointment, can be considered outdated when current time exceeds their ending hour.
		else if (((int)(dayTimestamp.getTime() / 1000)) > endHourTimestamp)
			outdated = true;
		
		return outdated;
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
	
	public int getStartHourTimestamp() {
		return startHourTimestamp;
	}
	
	public int getEndHourTimestamp() {
		return endHourTimestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getCreated_at() {
		return created_at;
	}
	
}