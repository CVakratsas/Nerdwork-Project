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
		ratingCounter = 0;
	}
	
	/* Methods of Course class */
	
	public void addProfessor(Professor p) {
		if(!professors.contains(p)) {
			professors.add(p);
		}
	}
	
	public void removeProfessor(Professor p) {
		int pIndex;
		
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
	
	public Double getRating() {
		return (double) (rating/ratingCounter);
	}
	
}
