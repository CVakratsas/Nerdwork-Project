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

import application.Timeslot.Availability;

public class Professor extends User {
	
	/*Professor attributes are here*/

	private ArrayList<Student> studentsRated; // List that contains all the students who have rated the professor
	private ArrayList<Timeslot> timeslots; // A Professor's available Timeslots
	private ArrayList<Timeslot> pendingAppointments; // Appointments that have not been accepted by "this" Professor object yet
	
	/*Professor Constructor is here*/
	
	public Professor(String id, String name, String displayName) {
		super(id, name, displayName);
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
	 * Method used to set a value at the description attribute of a course 
	 * object.
	 * editCourseDescription, receives a Course class object (course object
	 * to set a value at its description attribute) and a String class object
	 * (the value that the Course.description attribute will be set at), as parameters
	 * and is a void type method.
	 */
	public void editCourseDescription(Course course, String description) {
		course.setDescription(description);
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
	 * Method used to set a value at the credit attribute of a course 
	 * object.
	 * editCourseName, receives a Course class object (course object
	 * to set a value at its credit attribute) and a Integer class object
	 * (the value that the Course.credit attribute will be set at), as parameters
	 * and is a void type method.
	 */
	public void editCourseCredit(Course course, Integer credit) {
		course.setCredit(credit);
	}
	
	/*Professor methods regarding appointments are here*/
	
	/*
	 * Method used to create a new Timeslot for appointment requests (marked AVAILABLE).
	 * addAvailableDate, receives a String class object (the date and hour in a special
	 * format("dd/mm/yyyy")), as a parameter and is a void type method.
	 */
	public void addAvailableDate(String date) {
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
		SimpleDateFormat sdf = timeslot.getDateFormat(); // Our Date format
		String timeslotDate = sdf.format(timeslot.getDate());
		Timeslot tempTimeslot = new Timeslot(timeslotDate);
		
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
		// Επίσης ίσως μια διαφορετική υλοποίηση της deny
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
	
	/*Professor class Getters and Setters: */
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
	
	public ArrayList<Timeslot> getRequestedAppointments() {
		return pendingAppointments;
	}
	
}
