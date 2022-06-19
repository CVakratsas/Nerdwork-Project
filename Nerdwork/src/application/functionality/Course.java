/*
 * The Course class, is used to create objects that represent,
 * courses for a University. Objects of the classes Professor 
 * and Student can react with objects of the Course class,
 * according to their needs.
 */

package application.functionality;

import java.util.ArrayList;

public class Course {
	
	/* Course Constructor is here: */
	
	private String id;
	private String name;
	private int orientation;
	private static final int ECTS = 5;
	private int semester;
	private float rating;
	private ArrayList<Professor> associatedProfessors; // Professors teaching each lesson
	
	public Course(String id, String name, ArrayList<String> associatedProfessorsId, float rating, int semester, ArrayList<Professor> allProfessors, int orientation) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.semester = semester;
		this.orientation = orientation;
		this.associatedProfessors = new ArrayList<Professor>();
		
		for (Professor p : allProfessors)
			for (String associatedProfessorId : associatedProfessorsId)
				if (associatedProfessorId.equals(p.getUserId()))
					associatedProfessors.add(p);
	}
	
	public String toString(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	
	public int getOrientation() {
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