/*
 * The Course class, is used to create objects that represent,
 * courses for a University. Objects of the classes Professor 
 * and Student can react with objects of the Course class,
 * according to their needs, by the use of certain methods, 
 * provided by the class.
 */

package application;

import java.util.ArrayList;

public class Course {
	
	/* Course Constructor is here: */
	
	private String id;
	private String name;
	private String orientation; // There are three types of the orientation attribute
	private static final int ECTS = 5;
	private int semester;
	private float rating;
	private ArrayList<Student> studentsRated; // List that contains all the students who have rated the course
	private ArrayList<Professor> associatedProfessors; // Professors teaching each lesson
	
	
	/* Course class attribute initialization: */
	
	public Course(String id, String name, ArrayList<String> associatedProfessorsId, float rating, int semester, ArrayList<Professor> allProfessors) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.semester = semester;
		this.associatedProfessors = new ArrayList<Professor>();
		
		for (Professor p : allProfessors)
			for (String associatedProfessorId : associatedProfessorsId)
				if (associatedProfessorId.equals(p.getUserId()))
					associatedProfessors.add(p);
	}
	
	/* Methods of Course class */
	
	/*
	 * Method that parses the this.name attribute to a String
	 * (overlaps the toString Java method).
	 * toString, has no parameters and returns a String variable 
	 * (the name attribute, of "this" Course object).
	 */
	public String toString(){
		return name;
	}
	
	/* Getter and Setter methods of the Course class: */
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getOrientation() {
		return orientation;
	}
	
	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public int getSemester() {
		return semester;
	}

	public int getECTS() {
		return ECTS;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public ArrayList<Professor> getProfessors() {
		return associatedProfessors;
	}
}