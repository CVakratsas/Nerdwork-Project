/*
 * Class that contains information about the hours and dates
 * a Professor is available for an appointment with a Student
 * and also for the status of the Students' requests for appointments.
 */

package application.functionality;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Timeslot {
	
	private int id;
	private String studentId;
	private int professorId;
	private int startHourTimestamp; // Seconds since 1st January 1970 00:00:00 for startHour
	private int endHourTimestamp; // // Seconds since 1st January 1970 00:00:00 for endHour
	private int day; // Stores the day of month of the appointment
	private int startHour; // Stores the starting hour of the appointment
	private int endHour; // Stores the ending hour of the appointment
	private int status; //0 = Not Confirmed, 1 = Confirmed, 2 = Cancelled
	private String created_at;
	
	// Constructor used for available Timeslots
	public Timeslot(int startHourTimestamp, int endHourTimestamp) {
		this.startHourTimestamp = startHourTimestamp;
		this.endHourTimestamp = endHourTimestamp;
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
	 * This method is used to return the date of the available Timeslot
	 * in a Date format.
	 */
	public static HashMap<String, Integer> getDateInfo(Date date) {
		Calendar calendarTimestamp = Calendar.getInstance();
		HashMap<String, Integer> availableDate = new HashMap<>();
		
		calendarTimestamp.setTime(date);
		
		availableDate.put("month", calendarTimestamp.get(Calendar.MONTH) + 1);
		availableDate.put("day", calendarTimestamp.get(Calendar.DAY_OF_MONTH));
		availableDate.put("hour", calendarTimestamp.get(Calendar.HOUR_OF_DAY));
		availableDate.put("minutes", calendarTimestamp.get(Calendar.MINUTE));
		
		return availableDate;
	}

	public HashMap<String, Date> getAppointment(){
		HashMap<String, Date> appointment = new HashMap<String, Date>();
		Date dateStartTimestamp = new Date((long)startHourTimestamp * 1000);
		Date dateEndTimestamp = new Date((long)endHourTimestamp * 1000);
		
		appointment.put("startHour", dateStartTimestamp);
		appointment.put("endHour", dateEndTimestamp);
		
		return appointment; 
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