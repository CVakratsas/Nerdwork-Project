package application;

import java.util.ArrayList;

public class Course {
	
	/* Course class properties: */
	
	private String name;
	private String orientation;
	private String description;
	private Integer semester;
	private Integer credit;
	private Integer rating;
	private Integer ratingCounter;
	private ArrayList<Professor> professors;
	
	/* Course class property initialization: */
	
	public Course(String name, String orientation, String description, Integer semester, Integer credit) {
		this.name = name;
		this.orientation = orientation;
		this.description = description;
		this.semester = semester;
		this.credit = credit;
	}
	
	/* Methods of Course class */
	
	public void addProfessor(Professor p) {
		if(!professors.contains(p)) {
			professors.add(p);
		}
		//p.getCourses().add(this);
	}
	
	public void removeProfessor(Professor p) {
		int pIndex;
		//int courseIndex;
		
		if(professors.contains(p)) {
			pIndex = professors.indexOf(p);	
			professors.remove(pIndex);
		}
			
		//courseIndex = p.getCourses().indexOf(this);
		//p.getCourses().remove(courseIndex);
	}
	
	public void addRate(Integer star) {
		rating += star;
		ratingCounter++;
	}
	
	public String toString(){
		return name;
	}

	/* Getter and Setter methods of the Course class: */
	
	public String getName() {
		return name;
	}

	public String getOrientation() {
		return orientation;
	}

	public Integer getSemester() {
		return semester;
	}

	public ArrayList<Professor> getProfessors() {
		return professors;
	}
	
	public Double getRating() {
		return (double) (rating/ratingCounter);
	}
}
