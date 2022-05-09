package application;

import java.util.ArrayList;

public class User {
	
	/*User properties are here*/

	protected String id;
	protected String password;
	protected String name;
	protected String email;
	protected String description;
	protected ArrayList<Course> myCourses;
	protected ArrayList<Timeslot> myAppointments;
	
	/*User Constructor is here*/
	
	public User(String id, String password, String name, String email, String description) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.description = description;
		myCourses = new ArrayList<>();
		myAppointments = new ArrayList<>();
	}
	
	/*User methods (except getters and setters) are here*/
	
	public String toString() {
		return id;
	}
	
	public void addCourse(Course course) {
		if(!myCourses.contains(course)) {
			myCourses.add(course);
		}
	}
	
	public void removeCourse(Course course) {
		myCourses.remove(course);
	}
	
	/*User Getters and Setters methods are here*/
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
