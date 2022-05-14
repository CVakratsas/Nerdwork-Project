package application;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/*Code for password checking found here: https://stackoverflow.com/a/41697673 and modified
	to check if there is at least one upper and one lower letter
	Pattern documentation: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html*/
	public boolean setPassword(String password) {
		if(password.length()>=8) {
	        Pattern upperLetter = Pattern.compile("[A-Z]");
	        Pattern lowerLetter = Pattern.compile("[a-z]");
	        Pattern digit = Pattern.compile("[0-9]");
	        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

	       Matcher hasUpperLetter = upperLetter.matcher(password);
	       Matcher hasLowerLetter = lowerLetter.matcher(password);
	       Matcher hasDigit = digit.matcher(password);
	       Matcher hasSpecial = special.matcher(password);

	       if (hasUpperLetter.find() && hasLowerLetter.find() && hasDigit.find() && hasSpecial.find()) {
	    	   this.password = password;
	    	   return true;
	       }
	       else 
	    	   return false;
		}
		else
			return false;
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

	public boolean setEmail(String email) {
		int atIndex = email.indexOf("@");
		String username = email.substring(0, atIndex);
		String domain = email.substring(atIndex, email.length());
		
		if (!domain.matches("@uom.edu.gr") && !domain.matches("@uom.gr") || username.matches("")) {
			return false;
		}
		else {
			this.email = email;
			return true;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
