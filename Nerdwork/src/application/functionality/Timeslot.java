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
import java.util.TimeZone;

public class Timeslot {
	
	public static final String Days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}; 
	public static final String Months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "December"};
	private int id; // Appointment's unique identifier.
	private String studentId; // Unique user database key.
	private int professorId; // Unique porfessor identifier
	private long startHourTimestamp; // Milieconds since 1st January 1970 00:00:00 for startHour
	private long endHourTimestamp; // Miliseconds since 1st January 1970 00:00:00 for endHour
	private int status; // 0 = Not Confirmed, 1 = Confirmed, 2 = Cancelled, 3 = Available 
	// Change to availability
	private String created_at;
	
	// Constructor used for available Timeslots
	public Timeslot(int startHourTimestamp, int endHourTimestamp, int status) {
		this.startHourTimestamp = ((long)startHourTimestamp) * 1000;
		this.endHourTimestamp = ((long)endHourTimestamp) * 1000;
		this.status = status;
	}
	
	// Constructor used for Timeslots when a Student requested an appointment
	public Timeslot(int id, String studentId, int professorId, int startHourTimestamp, int endHourTimestamp, int status, String created_at) {
		this.id = id;
		this.studentId = studentId;
		this.professorId = professorId;
		this.startHourTimestamp = ((long)startHourTimestamp) * 1000;
		this.endHourTimestamp = ((long)endHourTimestamp) * 1000;
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
		if (dayTimestamp.getTime() > startHourTimestamp && status != 1)
			outdated = true;
		
		// A reserved appointment, can be considered outdated when current time exceeds their ending hour.
		else if (dayTimestamp.getTime() > endHourTimestamp)
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
	
	/* Returns seconds since 1st January 1970 00:00:00 of startHour*/
	public int getStartHourTimestamp() {
		return (int)(startHourTimestamp / 1000);
	}

	/* Returns seconds since 1st January 1970 00:00:00 of endHour*/
	public int getEndHourTimestamp() {
		return (int)(endHourTimestamp / 1000);
	}
	
	public long getStartHourTimestampMili() {
		return startHourTimestamp;
	}
	
	public long getEndHourTimestampMili() {
		return endHourTimestamp;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCreated_at() {
		return created_at;
	}
}