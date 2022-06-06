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

import application.Timeslot.Availability;

public class Professor extends User {
	
	/*Professor attributes are here*/
	
	private String phone;
	private String profilePhoto;
	private String bio;
	private String office;
	private int professorId;
	private float rating;
	private ArrayList<Timeslot> timeslots; // A Professor's available Timeslots
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
		timeslots = new ArrayList<>();
		studentsRated = new ArrayList<>();
		pendingAppointments = new ArrayList<>();
	}
	
	// Constructor for professor login
	public Professor(String userId, String username, String displayName, int accountType, int professorId) {
		super(userId, username, displayName, accountType);
		this.professorId = professorId;
		studentsRated = new ArrayList<>();
		timeslots = new ArrayList<>();
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
	
	/*Professor methods regarding appointments are here*/
	
	/*
	 * Method used to create a new Timeslot for appointment requests (marked AVAILABLE).
	 * addAvailableDate, receives a String class object (the date and hour in a special
	 * format("dd/mm/yyyy")), as a parameter and is a void type method.
	 */
	public void addAvailableDate(HashMap<String, Integer> date) {
		timeslots.add(new Timeslot(date));
	}
	
	/*
	 * Method used to delete a Timeslot for appointment requests (remove it from "this" Calendar).
	 * removeAvailableDate, receives a Timeslot class object (the timeslot to be deleted), 
	 * as a parameter and is a void type method.
	 */
	public void removeAvailableDate(Timeslot timeslot) {
		timeslots.remove(timeslot);
	}
	
	/*
	 * Method used for appointment requests of Student class objects,
	 * to "this" Professor object. It adds their request as a PENDING 
	 * one in the pendingAppointments array list.
	 * addAppointmentRequest, receives a Student class object (student that
	 * requests an appointment) and a Timeslot class object (a selected by
	 * the student timeslot, which "this" Professor object, marked as available),
	 * as parameters and is a void type method
	 */
	public void addAppointmentRequest(Student student, Timeslot timeslot) {
		Timeslot tempTimeslot = timeslot;
		
		tempTimeslot.setStudent(student);
		pendingAppointments.add(tempTimeslot);
	}

	/*
	 * Method used for acceptance of appointment requests of Student class objects,
	 * to "this" Professor object. It marks their request as a RESERVED one and 
	 * removes it from the pendingAppointments array list. Automatically denies all others
	 * of the same timeslot.
	 * acceptAppointment, receives a Timeslot class object (the timeslot selected
	 * by a student and his request of appointment at that timeslot is marked as PENDING),
	 * as parameter and is a void type method
	 */
	public void acceptAppointment(Timeslot timeslot) {
		int i = 0;
		Student student = timeslot.getStudent();
		
		// Accept the selected appointment:
		for (Timeslot t: timeslots) {
			if (timeslot.getStudent().equals(student) && timeslot.getDate().equals(t.getDate())) {
				t.setAvailability(Availability.RESERVED);
				pendingAppointments.remove(timeslot);
		
			}
		}
		
		// Deny automatically all others:
		// ������ ���� ��� ����������� ��������� ��� deny
		while (i < pendingAppointments.size()) {
			if (pendingAppointments.get(i).getDate().equals(timeslot.getDate())) {
				pendingAppointments.remove(pendingAppointments.get(i));
				i -= 1; // Because we remove the current element
			}
			
			i += 1;
		}
	}

	/*
	 * Method used for denial of appointment requests of Student class objects,
	 * to "this" Professor object. It marks their request as an AVAILABLE one and 
	 * removes it from the pendingAppointments array list.
	 * denyAppointment, receives a Timeslot class object (the timeslot selected
	 * by a student and his request of appointment at that timeslot is marked as PENDING),
	 * as parameter and is a void type method
	 */
	public void denyAppointment(Timeslot timeslot) {
		int pIndex; // Index of timeslot parameter
		
		pIndex = timeslots.indexOf(timeslot);
		timeslots.get(pIndex).setStudent(null);
		
		timeslots.get(pIndex).setAvailability(Availability.AVAILABLE);
		pendingAppointments.remove(timeslot);
	}
	
	/*
	 * Method used for canceling reserved appointments of Student class objects,
	 * to "this" Professor object. It marks their request as AVAILABLE.
	 * denyAppointment, receives a Timeslot class object (the timeslot the student
	 * reserved for an appointmetn with "this" Professor class object),
	 * as parameter and is a void type method
	 */
	public void cancelAppointment(Timeslot timeslot) {
		Integer pIndex;
		
		pIndex = timeslots.indexOf(timeslot);
		timeslots.get(pIndex).setStudent(null);
		timeslots.get(pIndex).setAvailability(Availability.AVAILABLE);
	}
	
	/*
	 * Method used to check if "this" Professor object already contains 
	 * this.timeslots attribute and also if it is updated.
	 * It returns true or false (returning values explained below) and 
	 * receives an ArrayList object contaning HashMap objects with 
	 * a String object as key and an Integer as a value, which represents 
	 * Timeslot objects from the data base.
	 */
	public boolean checkTimelsots(ArrayList<HashMap<String, Integer>> dates) {
		int nextDate = 0; // Index of the dates ArrayList object
		
		// Checks if all values contained in the dates object are the same as this.timeslots object
		for (Timeslot timeslot : timeslots) {
			if (!timeslot.getDate().get("day").equals(dates.get(nextDate).get("day")))
				if (!timeslot.getDate().get("startHour").equals(dates.get(nextDate).get("startHour")))
					if (!timeslot.getDate().get("endHour").equals(dates.get(nextDate).get("endHour")))
						return false; // The professor contains an outdated timeslots object
			
			nextDate++;
		}
	
		return true; // The professor already contains the exact same values in the timeslots object
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
	
	/*Professor class Getters and Setters: */
	
	public int getProfessorId() {
		return professorId;
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
