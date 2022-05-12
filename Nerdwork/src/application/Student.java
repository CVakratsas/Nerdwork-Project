package application;

import java.util.ArrayList;

public class Student extends User {
	
	/*Student properties are here*/
	
	private Double gpa;
	private String orientation;
	private ArrayList<Double> courseGrades;
	
	/*Student Constructor is here*/
	
	public Student(String id, String password, String name, String email, String description, String orientation) {
		super(id, password, name, email, description);
		this.gpa = null;
		this.orientation = orientation;
		courseGrades = new ArrayList<>();
	}
	
	/*Student methods regarding courses are here*/

	public void rateCourse(Course course, Integer stars) {
		course.addRate(stars);
	}
	
	public void rateProfessor(Professor professor, Integer stars) {
		professor.addRate(stars);
	}
	
	public void addGrade(Course course, Double grade) {
		Integer pIndex;
		
		pIndex = myCourses.indexOf(course);
		courseGrades.set(pIndex, grade);
	}
	
	public void addCourse(Course course) {
		super.addCourse(course);
		courseGrades.add(null);
	}
	
	public void removeCourse(Course course) {
		Integer pIndex;
		
		pIndex = myCourses.indexOf(course);
		courseGrades.remove(pIndex);
		super.removeCourse(course);
	}
	
	/*Student methods regarding appointments are here*/
	
	public void requestAppointment(Professor professor, Timeslot timeslot) {
		professor.addAppointmentRequest(this, timeslot);
	}
	
	public void cancelAppointment(Professor professor, Timeslot timeslot) {
		professor.cancelAppointment(timeslot);
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