package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

public class GuiController {
	
	// Class attributes
	private URestController controller; // Not to be user by GUI.
 	private ArrayList<Course> allCourses; // All courses.
 	private ArrayList<Course> myCourses; // Courses taught by "this" professor/Courses attended by "this" student.
 	// The 2 types of User:
 	private Student student;
 	private Professor professor;
 	
 	// Constuctor:
 	public GuiController() {
 		controller = new URestController();
 		allCourses = new ArrayList<Course>();
 		myCourses = new ArrayList<Course>();
 	}
 	
 	/*
 	 * Method used to register user.
 	 */
 	public boolean register(String username, String password, String displayName, String email) throws IOException {
 		if (checkPassword(password))
 			return controller.doRegister(username, password, displayName, email); // Email incorrect or username already exists.
 		
 		return false; // Password incorrect.
 	}
 	
 	/*
 	 * Method used for logging in.
 	 * login returns true if the login was successful or false otherwise
 	 */
 	public boolean login(String username, String password) throws IOException, ParseException {
 		FLoginResponse flr;
 		
 		flr = controller.doLogin(username, password);
 		
 		if (flr.isSuccess) {
	 		if (flr.accountType == 0) {
	 			professor = null;
	 			student = new Student(flr.userId, flr.username, flr.displayName);
	 		}
	 		else {
	 			student = null;
	 			professor = new Professor(flr.userId, flr.username, flr.displayName);
	 		}
 		}

 		return flr.isSuccess;
 	}
 	
 	/*
 	 * Method used to get all the Courses contained in the database.
 	 * It returns all of these Courses.
 	 */
 	public ArrayList<Course> getAllCourses() throws IOException, ParseException{
 		ArrayList<FSubjectsResponse> fsr = controller.getAllSubjects();
		
 		if (allCourses.size() != fsr.size()) {
 			allCourses.clear();
 			
 			for (FSubjectsResponse i : fsr) 
 				allCourses.add(new Course(i.id, i.name, i.rating, i.semester));
 		}
 		
 		return allCourses;
 	}
 	
 	
 	
 	public ArrayList<Course> getMyCourses(){
 		return myCourses;
 	}
 	
 	/*
 	 * Method that returns the User. As mentioned in the login method,
 	 * the user type that is not logged in will be pointing to null. 
 	 * The other user type will only be pointing to a User object.
 	 */
 	public User getUser() {
 		
 		if (professor == null)
 			return student;
 		
 		return professor;
 	}
 	
	/*
	 * Method for setting a password for "this" Users object and also check
	 * if the inputed password has been written according to our password pattern.
	 * setPassword, receives a String object (password inputed by the user), as a 
	 * parameter and is a boolean type method, returning true if the password provided,
	 * meets the pattern and false otherwise.
	 * Some of the following code is product of others. Sources follow:
	 * 
	 * Code for password checking found here: https://stackoverflow.com/a/41697673 and modified
	 * to check if there is at least one upper and one lower letter
	 * Pattern documentation: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
	 */
	private boolean checkPassword(String password) {
		// Password verification begins here:
		if(password.length()>=8) {
	       Pattern upperLetter = Pattern.compile("[A-Z]");
	       Pattern lowerLetter = Pattern.compile("[a-z]");
	       Pattern digit = Pattern.compile("[0-9]");
	       Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	       
	       // The above code serves as our password pattern 
	       
	       Matcher hasUpperLetter = upperLetter.matcher(password);
	       Matcher hasLowerLetter = lowerLetter.matcher(password);
	       Matcher hasDigit = digit.matcher(password);
	       Matcher hasSpecial = special.matcher(password);
	       
	       // Password pattern checking begins here:
	       if (hasUpperLetter.find() && hasLowerLetter.find() && hasDigit.find() && hasSpecial.find()) 
	    	   return true;
	       else
	    	   // Password that has at least one upper letter, lower letter, digit and special character, only accepted
	    	   return false;
		}
		else
			// Password of 8 letters and above only accepted
			return false;
	}
}
