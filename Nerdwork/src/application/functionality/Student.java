/* Student class, containing methods and attributes for the Student
 * objects' functionalities.
 */

package application.functionality;

import java.util.ArrayList;

public class Student extends User {
	
	/*Student attributes are here*/
	
	private Double gpa;
	
	/*
	 * courseGrades functions as follows: it contains the grades of the 
	 * courses attended by "this" Student object and each index of this array
	 * keeps the grade of the course being contained in the corresponding index,
	 * as of the array myCourses.
	 */
	private ArrayList<Double> courseGrades;
	
	public Student(String userId, String username, String displayName, int orientation) {
		super(userId, username, displayName, orientation);
		this.gpa = null;
		courseGrades = new ArrayList<>();
	}
	
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
	
	/*User Getters and Setters methods are here*/

	public Double getGpa() {
		return gpa;
	}

	public ArrayList<Double> getCourseGrades() {
		return courseGrades;
	}
}