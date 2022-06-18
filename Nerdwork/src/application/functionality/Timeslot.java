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
	private int status; // 0 = Not Confirmed, 1 = Confirmed, 2 = Cancelled, 3 = Available
	private String created_at;
	
	// Constructor used for available Timeslots
	public Timeslot(int startHourTimestamp, int endHourTimestamp, int status) {
		this.startHourTimestamp = startHourTimestamp;
		this.endHourTimestamp = endHourTimestamp;
		this.status = status;
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
	 * Method used to get data contained in the Date objects, concerning 
	 * dates, in a simpler way.
	 * It returns a HashMap with key of the type String (month, day, hour, 
	 * minutes) and with values of type Integer (value for each of the aforementioned
	 * keys) and receives a Date object as parameter (the date from which to extract 
	 * these information).
	 */
	public static HashMap<String, Integer> getDateInfo(Date date) {
		Calendar calendarTimestamp = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		HashMap<String, Integer> availableDate = new HashMap<>();
		
		calendarTimestamp.setTime(date);
		
		availableDate.put("month", calendarTimestamp.get(Calendar.MONTH) + 1);
		availableDate.put("day", calendarTimestamp.get(Calendar.DAY_OF_MONTH));
		availableDate.put("hour", calendarTimestamp.get(Calendar.HOUR_OF_DAY));
		availableDate.put("minutes", calendarTimestamp.get(Calendar.MINUTE));
		
		return availableDate;
	}
	
	/*
	 * Method used to return the start date and end date of an appointment.
	 * It returns a HashMap with key of the type String (startHour, endHour)
	 * and with values of the type Date (Date objects representing the end and
	 * start hour of the appointment) and receives no parameters.
	 */
	public HashMap<String, Date> getAppointment(){
		HashMap<String, Date> appointment = new HashMap<String, Date>();
		Date dateStartTimestamp = new Date((long)startHourTimestamp * 1000);
		Date dateEndTimestamp = new Date((long)endHourTimestamp * 1000);
		
		appointment.put("startHour", dateStartTimestamp);
		appointment.put("endHour", dateEndTimestamp);
		
		return appointment; 
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
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
}