package application;

import java.util.ArrayList;

public abstract class Orientation {
	private String name;
	private ArrayList<Course> courses;
	
	public Orientation(String name) {
		this.name = name;
	}
	
	public void addSubjects(Course c) {
		courses.add(c);
	}
	
	public String toString() {
		String str = "List of subjects:\n";
		for(Course c: courses) {
			str += "\t*" + c.getName() + "\n";
		}
		return str;
	}

	public ArrayList<Course> getSubjects() {
		return courses;
	}
}