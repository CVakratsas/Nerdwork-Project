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

	protected String id;
	protected String password;
	protected String name;
	protected String email;
	protected String displayName;
	//protected String description;
	/*
	 * The above five attributes keep track of personal Student/Professor,
	 * concerning the profile they have built.
	 */
	
	protected ArrayList<Course> myCourses; // Courses attended by Students or taught by Professors
	protected ArrayList<Timeslot> myAppointments; // Appointments made by Students, and available timeslots, for appointment with a Professor
	
	/*User Constructor is here*/
	
	public User(String id, String name, String displayName) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
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
		return id;
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
	
	/* User methods regarding passwords and emails are here: */
	
	/*
	 * Method that checks if the inputed - by "this" User - e-mail is valid.
	 * It also creates a new email for "this" (only if it is a newly created one) User, 
	 * by setting a value to the email attribute.
	 * setEmail, receives a String object (the email inputed by the user), as a parameter
	 * and is a boolean class returning true if the email is valid and false otherwise.
	 */
	public boolean setEmail(String email) {
		int atIndex = email.indexOf("@");
		String username = email.substring(0, atIndex);
		String domain = email.substring(atIndex, email.length());
		
		// E-mail validation begins here:
		if (!domain.matches("@uom.edu.gr") && !domain.matches("@uom.gr") || username.matches("")) {
			return false;
		}
		else {
			// E-mail of the type username@uom.(edu).gr, with username a non null String, is only valid.			
			this.email = email;
			return true;
		}
	}
	
	/*User Getters and Setters methods are here*/
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
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
}
