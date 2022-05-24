package application.functinonality;

import java.util.ArrayList;

public class Subject {
	private String name;
	private String orientation;
	private int semester;
	private ArrayList<Professor> teachers;
	
	public Subject(String name, String orientation, int semester) {
		this.name = name;
		this.orientation = orientation;
		this.semester = semester;
	}
	
	public void addProfessor(Professor p) {
		teachers.add(p);
		p.getSubjects().add(this);
	}
	
	public String toString(){
		String str = "Subject name: " + name + "\n"
				+ "Semester: " + semester + "\n"
				+ "orientation: " + orientation;
		return str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public ArrayList<Professor> getTeachers() {
		return teachers;
	}
}
