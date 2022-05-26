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
 	private ArrayList<Professor> allProfessors;
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
	 			student = new Student(flr.userId, flr.username, flr.displayName, flr.accountType);
	 			student.setEmail(controller.getUserProfile(student.getUserId()).email);
	 		}
	 		else {
	 			student = null;
	 			professor = new Professor(flr.userId, flr.username, flr.displayName, flr.accountType, flr.associatedProfessorId);
	 			professor.setBio(controller.getUserProfile(professor.getUserId()).bio);
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
		
		// The first if statement is used to check if the user has already accessed the page 
 		// for all courses. In that way we do not spend time refilling the array.
 		if (allCourses.size() != fsr.size()) {
 			allCourses.clear();
 			
 			for (FSubjectsResponse i : fsr) 
 				allCourses.add(new Course(i.id, i.name, i.rating, i.semester));
 		}
 		
 		return allCourses;
 	}
 	
 	/*
 	 * Method used by students in order to enroll to a course.
 	 * The method returns true only if the enrollment was successful.
 	 * It returns false if the enrollment failed (student already enrolled)
 	 * or because of the http request failure.
 	 */
 	public boolean courseEnrollment(String id) throws IOException {
 		return controller.enrollSubject(id);
 	}
 	
 	public boolean courseDisenrollment(String id) throws IOException {
 		return controller.disenrollSubject(id);
 	}
 	
 	public ArrayList<Course> getEnrolledCourses() throws IOException, ParseException{
 		ArrayList<String> enrolledCourses = controller.getEnrolledSubjects();
 		
 		// The first if statement is used to check if the user has already accessed the page 
 		// for his courses. In that way we do not spend time refilling the array.
 		if (enrolledCourses.size() != myCourses.size()) {
 			myCourses.clear();
 			
			for (int i = 0; i < enrolledCourses.size(); i++) 
				for (int j = 0; j < allCourses.size(); j++)	
					if (enrolledCourses.get(i).equals(allCourses.get(j).getId()))
						myCourses.add(allCourses.get(j));
 		}
 		
 		return myCourses;
 	}
 	
 	/*
 	 * Method used for rating a Course, by students. It checks if
 	 * the selected course is attended by the student and also updates 
 	 * attributes with new data.
 	 * Returns true if the rating was successful and false if it was not
 	 * (student already rated this course) or an error occurred on the side
 	 * of the server.
 	 */
 	public boolean rateCourse(int stars, String id) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedCourse = 0;
 		
 		// Checking if the course the Student chose to rate is attended by him
		for (Course course : myCourses) {
			if (course.getId().equals(id)) {
					success = controller.setSubjectRating(stars, id);
					break;
			}
			
			indexOfRatedCourse++;
		}
		
		// Update data with new values if rating was successful
 		if (success) {
 			float rating = controller.getSubjectRating(id);
 			
 			myCourses.get(indexOfRatedCourse).setRating(rating); // Update allCourses
 			
 			// Update myCourses
 			for (int i = 0; i < allCourses.size(); i++) {
 				if (allCourses.get(i).getId().equals(id)) {
 					allCourses.get(i).setRating(rating);
 					break;
 				}
 			}		
 		}
 		
 		return success;
 	}
 	
 	public float getCourseRating(String id) throws IOException, ParseException {
 		return controller.getSubjectRating(id);
 	}
 	
 	public int getMyCourseRating(String id) throws IOException, ParseException {
 		return controller.getMySubjectRating(id);
 	}
 	
 	public int getCourseECTS(Course course) {
 		return course.getECTS();
 	}
 	
 	public ArrayList<Professor> getAllProfessors(){
 		ArrayList<FProfessorsResponse> fpr = controller.getAllProfessors();
 		
 		for (FProfessorsResponse i : fpr)
 			allProfessors.add(new Professor())
 		
 		return fpr;
 	}
 	
 	public String getDisplayNameStudent() {
 		return student.getDisplayName();
 	}
 	
 	public String getDisplayName() {
 		return professor.getDisplayName();
 	}
 	
 	public String getUserNameStudent() {
 		return student.getUserName();
 	}
 	
 	public String getUserNameProfessor() {
 		return professor.getUserName();
 	}
 	
 	public String getEmailStudent() {
 		return student.getEmail();
 	}
 	
 	public String getEmailProfessor() {
 		return professor.getEmail();
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
