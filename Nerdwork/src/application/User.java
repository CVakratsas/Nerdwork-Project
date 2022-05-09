package application;

import java.util.ArrayList;

public class User {

	private String id;
	private String password;
	private String name;
	private String email;
	private String description;
	private ArrayList<Course> myCourses = new ArrayList<>();
	private ArrayList<Timeslot> myAppointments = new ArrayList<>();
	
	public void addCourse(Course course) {
		if(!myCourses.contains(course)) {
			myCourses.add(course);
		}
	}
	
	public void removeCourse(Course course) {
		myCourses.remove(course);
	}
	
}
