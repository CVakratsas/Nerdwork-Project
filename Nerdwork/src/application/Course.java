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
	
	private String name;
	private String orientation; // There are three types of the orientation attribute
	private String description;
	private Integer semester;
	private Integer credit;
	private Integer numOfStars; // Total number of stars for "this" Course object
	private Integer numOfRates; // Total number of students who have rated "this" Course object
	private ArrayList<Student> studentsRated; // List that contains all the students who have rated the course
	private ArrayList<Professor> professors; // Professors teaching each lesson
	
	/* Course class attribute initialization: */
	
	public Course(String name, String orientation, String description, Integer semester, Integer credit) {
		this.name = name;
		this.orientation = orientation;
		this.description = description;
		this.semester = semester;
		this.credit = credit;
		numOfRates = 0;
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
			
		//courseIndex = p.getCourses().indexOf(this);
		//p.getCourses().remove(courseIndex);
	}
	
	/* Methods regarding rating are here: */
	
	/*
	 * Method used by Students to rate "this" Course object, by
	 * a number of stars (from 0-5). The method keeps track
	 * of the number of students who have rated "this" Course object and
	 * the total number of stars they have given.
	 * addRate, gets an Integer (the stars selected by the Student) and a Student
	 * as a parameters and is a boolean type method.
	 * Returns true if the student's rate has been submitted, returns false if the student had already rated before.
	 */
	public boolean addRate(Student student, Integer star) {
		if (!studentsRated.contains(student)) {
			numOfStars += star;
			numOfRates++;
			studentsRated.add(student);
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Method used to calculate "this" Course object's rating,
	 * using the numOfStars and numOfRates attributes in a simple
	 * division.
	 * calcRating, receives no parameters and is a Double type method,
	 * returning the, previously mentioned, division's result.
	 */
	public Double calcRating() {
		return (double) (numOfStars/numOfRates);
	}

	/* Getter and Setter methods of the Course class: */
	
	public void setName(String name) {
		this.name = name;
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

	public ArrayList<Professor> getProfessors() {
		return professors;
	}
	
//	public Double getRating() {
//		return (double) (numOfStars/numOfRates);
//	}
	
}
