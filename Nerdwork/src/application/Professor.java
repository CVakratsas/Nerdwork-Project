package application;

import java.util.ArrayList;

import application.Timeslot.Availability;

public class Professor extends User {
	
	/*Professor properties are here*/
	
	private Integer rating;
	private Integer ratingCounter;
	private Calendar myCalendar;
	private ArrayList<Timeslot> pendingAppointments;
	
	/*Professor Constructor is here*/
	
	public Professor(String id, String password, String name, String email, String description) {
		super(id, password, name, email, description);
		rating = 0;
		ratingCounter = 0;
		pendingAppointments = new ArrayList<>();
	}
	
	/*Professor methods regarding courses are here*/
	
	public void addRate(Integer star) {
		rating += star;
		ratingCounter++;
	}
	
	public void editCourseName(Course course, String name) {
		course.setName(name);
	}
	
	public void editCourseOrientation(Course course, String orientation) {
		course.setOrientation(orientation);
	}
	
	public void editCourseDescription(Course course, String description) {
		course.setDescription(description);
	}
	
	public void editCourseSemester(Course course, Integer semester) {
		course.setSemester(semester);
	}
	
	public void editCourseCredit(Course course, Integer credit) {
		course.setCredit(credit);
	}
	
	/*Professor methods regarding appointments are here*/

	public void addAppointmentRequest(Student student, Timeslot timeslot) {
		timeslot.setStudent(student);
		pendingAppointments.add(timeslot);
	}
	
	public void acceptAppointment(Timeslot timeslot) {
		myCalendar.reserveTimeslot(timeslot);
		pendingAppointments.remove(timeslot);
	}
	
	public void denyAppointment(Timeslot timeslot) {
		myCalendar.freeTimeslot(timeslot);
		pendingAppointments.remove(timeslot);
	}
	
	public void cancelAppointment(Timeslot timeslot) {
		Integer pIndex;
		
		pIndex = myCalendar.getTimeslots().indexOf(timeslot);
		myCalendar.getTimeslots().get(pIndex).setStudent(null);
		myCalendar.getTimeslots().get(pIndex).setAvailability(Availability.AVAILABLE);
	}
	
	/*Professor class Getters and Setters: */
	
	public Double getRating() {
		return (double) (rating/ratingCounter);
	}
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	public ArrayList<Timeslot> getRequestedAppointments() {
		return pendingAppointments;
	}
	
}
