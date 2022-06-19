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
	private int professorId; // Is a unique professor identifier
	private float rating;
	private ArrayList<Timeslot> timeslots; // A Professor's available Timeslots
	
	// Constructor for getting professor information as student.
	public Professor(String displayName, int professorId, String email, String profilePhoto, String phone, String office, float rating, int orientation) {
		super(Integer.toString(professorId), null, displayName, orientation);
		this.professorId = professorId;
		this.email = email;
		this.profilePhoto = profilePhoto;
		this.phone = phone;
		this.office = office;
		this.rating = rating;
		timeslots = new ArrayList<Timeslot>();
	}
	
	// Constructor for professor login
	public Professor(String userId, String username, String displayName, int professorId, int orientation) {
		super(userId, username, displayName, orientation);
		this.professorId = professorId;
		timeslots = new ArrayList<Timeslot>();
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
	
	public void clearTimeslots() {
		timeslots.clear();
	}
	
	/*Professor class Getters and Setters: */
	
	public int getProfessorId() {
		return professorId;
	}
	
	/*
	 * Method used to create available Timeslots (with half hour distance of the start
	 * and end hours of the appointment).
	 * It returns nothing and receives two int type parameters the start and end hour
	 * of the appointment in the form of seconds passed since 1st January 1970 00:00:00 
	 * and two ArrayList consisting of Timeslot objects one representing MyAppointments
	 * and the other the Reserved. The last two parameters, are used to return all of the 
	 * appointments the professor has with the correct status each.
	 */
	public void addTimeslot(long startHourTimestamp, long endHourTimestamp, ArrayList<Timeslot> requestedTimeslots, ArrayList<Timeslot> reservedTimeslots) {
		Date dateStartTimestamp = new Date(startHourTimestamp);
		Date dateEndTimestamp = new Date(endHourTimestamp);
		// The two above objects, are used only for the if condition.
		
		long appointmentStartHourTimestamp = startHourTimestamp; // The start hour of the available hours of a professor for a student.
		long appointmentEndHourTimestamp = startHourTimestamp + (1800 * 1000); // The end hour of the available hours of a professor for a student.
		
		boolean isRequested = false;
		
		// The condition means: The appointments that can be made from starting hour
		// to end hour. Appointments last for 30 minutes each.
		for (int i = 0; i < ((dateEndTimestamp.getTime() - dateStartTimestamp.getTime()) / 1000) / 1800; i++) {
			Date appointmentDateStartHourTimestamp = new Date(appointmentStartHourTimestamp);
			Date appointmentDateEndHourTimestamp = new Date(appointmentEndHourTimestamp);
			
			//System.out.println("starting: " + appointmentDateStartHourTimestamp + " ending " + appointmentDateEndHourTimestamp);
			Timeslot timeslot = new Timeslot((int)(appointmentDateStartHourTimestamp.getTime() / 1000), (int)(appointmentDateEndHourTimestamp.getTime() / 1000), 3);
			
			if (!timeslot.checkOutdated()) {
				
				// Checks if the timeslot we created is a requested one (see getMyppointments method in GuiController).
				for (Timeslot requested : requestedTimeslots)
					if (requested.getStartHourTimestamp() == appointmentDateStartHourTimestamp.getTime() / 1000 && professorId == requested.getProfessorId()) {
						timeslot.setStatus(requested.getStatus());
						isRequested = true;
					}
				
				// Checks if the timeslot we created is a reserved one (see getReservedTimeslots method in GuiController)
				for (Timeslot reserved : reservedTimeslots)
					if (reserved.getStartHourTimestamp() == appointmentDateStartHourTimestamp.getTime() / 1000 && !isRequested)
						timeslot.setStatus(1);
						
				timeslots.add(timeslot);
				
				isRequested = false;
			}
			
			appointmentStartHourTimestamp = appointmentEndHourTimestamp;
			appointmentEndHourTimestamp += (1800 * 1000); 
		}
	}
	
	public void removeTimeslot(Timeslot timeslot) {
		timeslots.remove(timeslot);
	}
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
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
