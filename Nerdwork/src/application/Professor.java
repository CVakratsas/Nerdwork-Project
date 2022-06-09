/*
 * Professor class, providing methods for the functionalities
 * that a professor might need to make in out program, such as,
 * the management of the courses they teach and their appointments 
 * with their students. It also provides some methods for rating
 * purposes of the Professor objects.
 */

package application;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Professor extends User {
	
	/*Professor attributes are here*/
	
	private String phone;
	private String profilePhoto;
	private String bio;
	private String office;
	private int professorId;
	private float rating;
	private ArrayList<Timeslot> availableTimeslots; // A Professor's available Timeslots
	private ArrayList<Timeslot> bookedTimeslots; // A Professor's booked Timeslots
	private ArrayList<Student> studentsRated; // List that contains all the students who have rated the professor
	private ArrayList<Timeslot> pendingAppointments; // Appointments that have not been accepted by "this" Professor object yet
	
	/*Professor Constructor is here*/
	
	// Constructor for getting professor information as student.
	public Professor(String displayName, int professorId, String email, String profilePhoto, String phone, String office, float rating) {
		super(Integer.toString(professorId), null, displayName, 1);
		this.professorId = professorId;
		this.email = email;
		this.profilePhoto = profilePhoto;
		this.phone = phone;
		this.office = office;
		this.rating = rating;
		studentsRated = new ArrayList<>();
		pendingAppointments = new ArrayList<>();
	}
	
	// Constructor for professor login
	public Professor(String userId, String username, String displayName, int accountType, int professorId) {
		super(userId, username, displayName, accountType);
		this.professorId = professorId;
		studentsRated = new ArrayList<>();
		pendingAppointments = new ArrayList<>();
	}
	
	/* Professor methods regarding course are here: */
	
	/*
	 * Method used to set a value at the name attribute of a course 
	 * object.
	 * editCourseName, receives a Course class object (course object
	 * to set a value at its name attribute) and a String class object
	 * (the value that the Course.name attribute will be set at), as parameters
	 * and is a void type method.
	 */
	public void editCourseName(Course course, String name) {
		course.setName(name);
	}
	
	/*
	 * Method used to set a value at the orientation attribute of a course 
	 * object.
	 * editCourseOrientation, receives a Course class object (course object
	 * to set a value at its orientation attribute) and a String class object
	 * (the value that the Course.orientation attribute will be set at), as parameters
	 * and is a void type method.
	 */
	public void editCourseOrientation(Course course, String orientation) {
		course.setOrientation(orientation);
	}
	
	/*
	 * Method used to set a value at the semester attribute of a course 
	 * object.
	 * editCourseName, receives a Course class object (course object
	 * to set a value at its semester attribute) and a Integer class object
	 * (the value that the Course.semester attribute will be set at), as parameters
	 * and is a void type method.
	 */
	public void editCourseSemester(Course course, Integer semester) {
		course.setSemester(semester);
	}
	
	/*
	 * Method used to delete a Timeslot for appointment requests (remove it from "this" Calendar).
	 * removeAvailableDate, receives a Timeslot class object (the timeslot to be deleted), 
	 * as a parameter and is a void type method.
	 */
	
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
	
	/*Professor class Getters and Setters: */
	
	public int getProfessorId() {
		return professorId;
	}
	
	public void addAvailableTimeslot(int timestamp) {
		availableTimeslots.add(new Timeslot(timestamp));
	}
	
	public ArrayList<Timeslot> getAvailableTimeslots() {
		return availableTimeslots;
	}
	
	public ArrayList<Timeslot> getBookedTimeslots() {
		return bookedTimeslots;
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

	public String getBio() {
		return bio;
	}

	public float getRating() {
		return rating;
	}

	public ArrayList<Timeslot> getRequestedAppointments() {
		return pendingAppointments;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
}
