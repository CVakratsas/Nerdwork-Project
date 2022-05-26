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
	private String description;
	private int semester;
	private int credit;
	private float rating;
	private ArrayList<Student> studentsRated; // List that contains all the students who have rated the course
	private ArrayList<Professor> professors; // Professors teaching each lesson
	
	/* Course class attribute initialization: */
	
	public Course(String id, String name, float rating, int semester) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.semester = semester;
	}
	
	public Course(String name, String orientation, String description, Integer semester, Integer credit) {
		this.name = name;
		this.orientation = orientation;
		this.description = description;
		this.semester = semester;
		this.credit = credit;
		studentsRated = new ArrayList<>();
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
	
	/* Methods regarding professors are here: */
	
	/* 
	 * Method that adds a professor in the professors array list,
	 * of "this" Course object.
	 * addProfessor, gets a Professor object (Professor to add)
	 * as a parameter and is a void type method. 
	 */
	public void addProfessor(Professor professor) {
		if(!professors.contains(professor)) {
			professors.add(professor);
		}
	}
	
	/*
	 * Method that removes a professor from the professors array list,
	 * of "this" Course object.
	 * removeProfessor, gets a Professor object (Professor to remove)
	 * as a parameter and is a void type method. 
	 */
	public void removeProfessor(Professor professor) {
		int pIndex; // Index of the Professor object in the professors array list
		
		if(professors.contains(professor)) {
			pIndex = professors.indexOf(professor);	
			professors.remove(pIndex);
		}
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

	public Integer getSemester() {
		return semester;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getCredit() {
		return credit;
	}
	
	public float getRating() {
		return rating;
	}
	
	public ArrayList<Professor> getProfessors() {
		return professors;
	}
}
