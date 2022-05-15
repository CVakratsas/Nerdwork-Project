/*
 * Professor class, providing methods for the functionalities
 * that a professor might need to make in out program, such as,
 * the management of the courses they teach and their appointments 
 * with their students. It also provides some methods for rating
 * purposes of the Professor objects.
 */

package application;

import java.util.ArrayList;

import application.Timeslot.Availability;

public class Professor extends User {
	
	/*Professor attributes are here*/
	
	private Integer numOfStars; // Number of total stars given to "this" Professor object
	private Integer numOfRates; // Number of Student object rated "this" Professor object
	private Calendar myCalendar; // A Professor's available Timeslots
	private ArrayList<Timeslot> pendingAppointments; // Appointments that have not been accepted by "this" Professor object yet
	
	/*Professor Constructor is here*/
	
	public Professor(String id, String password, String name, String email, String description) {
		super(id, password, name, email, description);
		numOfStars = 0;
		numOfRates = 0;
		pendingAppointments = new ArrayList<>();
	}
	
	/*Professor methods regarding courses are here*/
	
	/* Professor methods regarding rating are here: */
	
	/*
	 * Method used by Students to rate "this" Professor object, by
	 * a number of stars (from 0-5). The method keeps track
	 * of the number of students who have rated "this" Professor object and
	 * the total number of stars they have given.
	 * addRate, gets an Integer (the stars selected by the Student)
	 * as a parameter and is a void type method.
	 */
	public void addRate(Integer star) {
		numOfStars += star;
		numOfRates++;
	}
	
	/*
	 * Method used to calculate "this" Professor object's rating,
	 * using the numOfStars and numOfRates attributes in a simple
	 * division.
	 * calcRating, receives no parameters and is a Double type method,
	 * returning the, previously mentioned, division's result.
	 */
	public Double calcRating() {
		return (double) (numOfStars/numOfRates);
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
	 * Method used for appointment requests of Student class objects,
	 * to "this" Professor object. It adds their request as a PENDING 
	 * one in the pendingAppointments array list.
	 * addAppointmentRequest, receives a Student class object (student that
	 * requests an appointment) and a Timeslot class object (a selected by
	 * the student timeslot, which "this" Professor object, marked as available),
	 * as parameters and is a void type method
	 */
	public void addAppointmentRequest(Student student, Timeslot timeslot) {
		timeslot.setStudent(student);
		pendingAppointments.add(timeslot);
	}

	/*
	 * Method used for acceptance of appointment requests of Student class objects,
	 * to "this" Professor object. It marks their request as a RESERVED one and 
	 * removes it from the pendingAppointments array list.
	 * acceptAppointment, receives a Timeslot class object (the timeslot selected
	 * by a student and his request of appointment at that timeslot is marked as PENDING),
	 * as parameter and is a void type method
	 */
	public void acceptAppointment(Timeslot timeslot) {
		myCalendar.reserveTimeslot(timeslot);
		pendingAppointments.remove(timeslot);
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
		myCalendar.freeTimeslot(timeslot);
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
		
		pIndex = myCalendar.getTimeslots().indexOf(timeslot);
		myCalendar.getTimeslots().get(pIndex).setStudent(null);
		myCalendar.getTimeslots().get(pIndex).setAvailability(Availability.AVAILABLE);
	}
	
	/*Professor class Getters and Setters: */
	
//	public Double getRating() {
//		return (double) (numOfStars/numOfRates);
//	}
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	public ArrayList<Timeslot> getRequestedAppointments() {
		return pendingAppointments;
	}
	
}
