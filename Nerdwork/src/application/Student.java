/*
 * Student class, containing methods and attributes for the Student
 * objects' functionalities, such as add or remove course, rating and
 * requesting/canceling an appointment with a Professor.
 */

package application;

import java.util.ArrayList;

public class Student extends User {
	
	/*Student attributes are here*/
	
	private Double gpa;
	private String orientation;
	
	/*
	 * courseGrades functions as follows: it contains the grades of the 
	 * courses attended by "this" Student object and each index of this array
	 * keeps the grade of the course being contained in the corresponding index,
	 * as of the array myCourses.
	 */
	private ArrayList<Double> courseGrades;
	
	/*Student Constructor is here*/
	
	public Student(String id, String password, String name, String email, String description, String orientation) {
		super(id, password, name, email, description);
		this.gpa = null;
		this.orientation = orientation;
		courseGrades = new ArrayList<>();
	}
	
	/*Student methods regarding ratings are here: */
	
	/*
	 * Method that is used to rate a course object, "this" Student object currently attends to, 
	 * with the help of the addRate method of the Course class.
	 * rateCourse, receives a Course object (course to rate) and an Integer object
	 * (stars given to it by "this" Student object), as parameters and is a void type method.
	 */
//	public void rateCourse(Course course, Integer stars) {
//		//if (myCourses.contains(course))	
//			course.addRate(this, stars);
//	}
	
	/*
	 * Method that is used to rate a Professor object, whom "this" Student object 
	 * is taught by, with the help of the addRate method of the Professor class.
	 * rateProfessor, receives a Professor object (professor to rate) and an Integer object
	 * (stars given to it by "this" Student object), as parameters and is a void type method.
	 */
	public void rateProfessor(Professor professor, Integer stars) {
		professor.addRate(this, stars);
	}
	
	/* Student methods regarding grades are here: */
	
	/*
	 * Method used to add "this" Student object's grade to one of the courses
	 * attended by him (updates "this" courseGrades array). 
	 * addGrade receives a Course object (course to add the grade to) and a Double object
	 * (the grade for the course), as parameters and is a void type method.
	 * After getting the new grade, GPA is calculated and updated in its variable.
	 */
	public void addGrade(Course course, Double grade) {
		Integer pIndex;
		
		pIndex = myCourses.indexOf(course);
		courseGrades.set(pIndex, grade);
		
		gpa = (double) 0;
		for (Double g : courseGrades) {
			gpa += g;
		}
		
		gpa /= courseGrades.size();
	}
	
	/* Student methods regarding courses are here: */
	
	/*
	 * Method used to add the courses (updating myCourses, via the super class User)
	 * that "this" Student object wants to attend to. 
	 * addCourse receives a Course object (course to add) as parameters and is a void 
	 * type method.
	 */
	public void addCourse(Course course) {
		super.addCourse(course);
		courseGrades.add(null);
	}
	
	/*
	 * Method used to remove the courses (updating myCourses, via the super class User)
	 * that "this" Student object wants to quit from. 
	 * removeCourse receives a Course object (course to remove), as parameters and is 
	 * a void type method.
	 */
	public void removeCourse(Course course) {
		Integer pIndex;
		
		pIndex = myCourses.indexOf(course);
		courseGrades.remove(pIndex);
		super.removeCourse(course);
	}
	
	/*Student methods regarding appointments are here*/
	
	/*
	 * Method used to request an appointment with a Professor object at a certain
	 * date and hour (timeslot).
	 * requestAppointment, receives a Professor object (professor with whom "this 
	 * Student object wants to meet) and a Timeslot object (the timeslot selected
	 * by "this" Student object for the appointment), as parameters and is a void
	 * type method
	 */
	public void requestAppointment(Professor professor, Timeslot timeslot) {
		professor.addAppointmentRequest(this, timeslot);
	}
	
	/*
	 * Method used to cancel a reserved appointment with a Professor object at a certain
	 * date and hour (timeslot).
	 * cancelAppointment, receives a Professor object (professor with whom "this 
	 * Student object wanted to meet) and a Timeslot object (the timeslot selected
	 * by "this" Student object for the appointment), as parameters and is a void
	 * type method
	 */
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