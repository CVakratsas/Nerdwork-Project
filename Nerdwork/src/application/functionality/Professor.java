/*
 * Professor class, providing methods for the functionalities
 * that a professor might need to make in our program, such as,
 * the management their appointments with their students. 
 */

package application.functionality;

import java.util.ArrayList;
import java.util.Date;

public class Professor extends User {
	
	/*Professor attributes are here*/
	
	private String phone;
	private String profilePhoto;
	private String office;
	private int professorId;
	private float rating;
	private ArrayList<Timeslot> availableTimeslots; // A Professor's available Timeslots
	
	// Constructor for getting professor information as student.
	public Professor(String displayName, int professorId, String email, String profilePhoto, String phone, String office, float rating, int orientation) {
		super(Integer.toString(professorId), null, displayName, orientation);
		this.professorId = professorId;
		this.email = email;
		this.profilePhoto = profilePhoto;
		this.phone = phone;
		this.office = office;
		this.rating = rating;
		availableTimeslots = new ArrayList<Timeslot>();
	}
	
	// Constructor for professor login
	public Professor(String userId, String username, String displayName, int professorId, int orientation) {
		super(userId, username, displayName, orientation);
		this.professorId = professorId;
		availableTimeslots = new ArrayList<Timeslot>();
	}
	
	/*
	 * Method that is used to get the courses a professor is teaching.
	 * The method returns an ArrayList of Course class objects and receives 
	 * an ArrayList of Course class objects (all the courses stored in our 
	 * database. This is used in order to define which course the professor 
	 * teaches, via the associatedProfessors attribute, of Course class objects).
	 */
	public ArrayList<Course> getCoursesTaught(ArrayList<Course> allCourses){
		/*
		 * For each course in allCourses we get the associatedProfessors
		 * attribute and use their professorId, in order to check if 
		 * the current course is being taught by "this" Professor object.
		 */
		myCourses.clear();
		
		for (Course course : allCourses)
			for (Professor associatedProfessor : course.getProfessors())
				if (associatedProfessor.getProfessorId() == this.professorId)
					myCourses.add(course);
		
		return myCourses;
	}
	
	public void clearAvailableTimeslots() {
		availableTimeslots.clear();
	}
	
	/*Professor class Getters and Setters: */
	
	public int getProfessorId() {
		return professorId;
	}
	
	/*
	 * Method used to create available Timeslots (with half hour distance of the start
	 * and end hours of the appointment).
	 * It returns nothing and receives two int type parameters the start and end hour
	 * of the appointment in the form of seconds passed since 1st January 1970 00:00:00.
	 */
	public void addAvailableTimeslot(int startHourTimestamp, int endHourTimestamp) {
		Date dateStartTimestamp = new Date((long)startHourTimestamp * 1000);
		Date dateEndTimestamp = new Date((long)endHourTimestamp * 1000);
		// The two above objects, are used only for the if condition.
		int appointmentStartHourTimestamp = startHourTimestamp; // The start hour of the available hours of a professor for a student.
		int appointmentEndHourTimestamp = startHourTimestamp + 1800; // The end hour of the available hours of a professor for a student.
		
		// The condition means: The appointments that can be made from starting hour
		// to end hour. Appointments last for 30 minutes each.
		for (int i = 0; i < ((dateEndTimestamp.getTime() - dateStartTimestamp.getTime()) / 1000) / 1800; i++) {
			Date appointmentDateStartHourTimestamp = new Date((long)appointmentStartHourTimestamp * 1000);
			Date appointmentDateEndHourTimestamp = new Date((long)appointmentEndHourTimestamp * 1000);
			Timeslot timeslot = new Timeslot((int)(appointmentDateStartHourTimestamp.getTime() / 1000), (int)(appointmentDateEndHourTimestamp.getTime() / 1000));
			
			if (!timeslot.checkOutdated())
				availableTimeslots.add(timeslot);
			
			appointmentStartHourTimestamp = appointmentEndHourTimestamp;
			appointmentEndHourTimestamp += 1800; 
		
		}
	}
	
	public ArrayList<Timeslot> getAvailableTimeslots() {
		return availableTimeslots;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getOffice() {
		return office;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public float getRating() {
		return rating;
	}

	public ArrayList<Timeslot> getRequestedAppointments() {
		return requestedAppointments;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
}
