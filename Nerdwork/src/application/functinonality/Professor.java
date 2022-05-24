package application.functinonality;

import java.util.ArrayList;

public class Professor {
	private String name;
	private String email;
	private ArrayList<Subject> subjects;
	
	public Professor(String name) {
		this.name = name;
	}
	
	public String toString() {
		String str = "Professor: " + name;
		return str;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
