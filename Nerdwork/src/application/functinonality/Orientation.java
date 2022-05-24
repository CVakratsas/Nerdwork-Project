package application.functinonality;

import java.util.ArrayList;

public abstract class Orientation {
	private String name;
	private ArrayList<Subject> subjects;
	
	public Orientation(String name) {
		this.name = name;
	}
	
	public void addSubjects(Subject s) {
		subjects.add(s);
	}
	
	public String toString() {
		String str = "List of subjects:\n";
		for(Subject s: subjects) {
			str += "\t*" + s.getName() + "\n";
		}
		return str;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
}