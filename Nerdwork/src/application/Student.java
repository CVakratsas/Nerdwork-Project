package application;

import java.sql.Date;
import java.util.ArrayList;

public class Student extends User {
	
	/*Student properties are here*/
	
	private Double gpa;
	private String orientation;
	private ArrayList<Double> courseGrades;
	
	/*Student Constructor is here*/
	
	public Student(String id, String password, String name, String email, String description, Double gpa, String orientation) {
		super(id, password, name, email, description);
		this.gpa = gpa;
		this.orientation = orientation;
		courseGrades = new ArrayList<>();
	}
	
	/*Student methods (except getters and setters) are here*/
	
	public void requestAppointment(Professor professor, Date date) {
		professor.addAppointmentRequest(new Timeslot(date, this));
	}
	
	public void cancelAppointment(Professor professor, Timeslot timeslot) {
		
	}

	public void rateCourse(Course course, Integer stars) {
		course.addRate(stars);
	}
	
	public void rateProfessor(Professor professor, Integer stars) {
		professor.addRate(stars);
	}
	
	/*User Getters and Setters methods are here*/

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Double getGpa() {
		return gpa;
	}

	public ArrayList<Double> getCourseGrades() {
		return courseGrades;
	}

}