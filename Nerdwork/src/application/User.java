/*
 * Class User is the superclass of both Student and Professor
 * subclasses. It contains mostly methods, that are commonly
 * used, but with a different outcome for each.
 */

package application;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	
	/*User attributes are here*/

	protected String userId;
	protected String username;
	protected String email;
	protected String displayName;
	protected int accountType;
	/*
	 * The above five attributes keep track of personal Student/Professor,
	 * concerning the profile they have built.
	 */
	
	protected ArrayList<Course> myCourses; // Courses attended by Students or taught by Professors
	protected ArrayList<Timeslot> myAppointments; // Appointments made by Students, and available timeslots, for appointment with a Professor
	
	/*User Constructor is here*/
	
	public User(String userId, String username, String displayName, int accountType) {
		this.userId = userId;
		this.username = username;
		this.displayName = displayName;
		this.accountType = accountType;
		myCourses = new ArrayList<>();
		myAppointments = new ArrayList<>();
	}
	
	/*User methods (except getters and setters) are here*/
	
	/*
	 * Method that parses the this.id attribute to a String
	 * (overlaps the toString Java method).
	 * toString, has no parameters and returns a String variable 
	 * (the name attribute, of "this" User object).
	 */
	public String toString() {
		return userId;
	}
	
	/* User methods regarding courses are here: */
	
	/*
	 * Method that a adds a course (course that will be taught by Professor/attended by Student)
	 * object in the myCourses array list of "this" User object. 
	 * addCourse, gets a Course object (course to add), as a parameter 
	 * and is a void type method
	 */
	public void addCourse(Course course) {
		if(!myCourses.contains(course)) {
			myCourses.add(course);
		}
	}
	
	/*
	 * Method that a removes a course (course taught by Professor/attended by Student)
	 * object from the myCourses array list of "this" User object. 
	 * removeCourse, gets a Course object (course to remove), as a parameter 
	 * and is a void type method
	 */
	public void removeCourse(Course course) {
		myCourses.remove(course);
	}
	
	/*User Getters and Setters methods are here*/
	
	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return username;
	}

	public void setUserame(String username) {
		this.username = username;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}
}
