/*
 * Class used in order to connect the GUI part of the program
 * with the database and vice versa. It provides methods for 
 * communication between both and also parses data so that they
 * can be used correctly by both. 
 * 
 * Note: This class is to be used instead of URestController, 
 * while the last must not be used anywhere else in the program
 */

package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.json.simple.parser.ParseException;

public class GuiController {
	
	private static GuiController c = null;
	
	public static GuiController getInstance() throws IOException, ParseException {
		if (c== null)
            c = new GuiController();
		return c;
	}
	
	// Class attributes
	private URestController controller; // Not to be user by GUI.
 	private ArrayList<Course> allCourses; // All courses.
 	private ArrayList<Course> myCourses; // Courses taught by "this" professor/Courses attended by "this" student.
 	private ArrayList<Professor> allProfessors;
 	// The 2 types of User:
 	private Student student;
 	private Professor professor;
 	
 	// Constuctor:
 	private GuiController() throws IOException, ParseException {
 		controller = new URestController();
 		allCourses = new ArrayList<Course>();
 		myCourses = new ArrayList<Course>();
 		allProfessors = new ArrayList<Professor>();
 	}
 	
 	/*
 	 * Method used to register user.
 	 * In order for the registration to be successful the user needs to pass
 	 * four String arguments: a username, a password, the name to be displayed 
 	 * to other users and an email (his academic email). It returns true if the 
 	 * registration was successful and false otherwise (described below).
 	 */
 	public boolean register(String username, String password, String displayName, String email) throws IOException {
 		if (checkPassword(password))
 			return controller.doRegister(username, password, displayName, email); // false if email incorrect or username already exists.
 		
 		return false; // Password incorrect.
 	}
 	
 	/*
 	 * Method used for logging in.
 	 * It returns true if the login was successful or false otherwise and
 	 * gets two String objects as parameters. First is the username that the
 	 * user who wants to login uses and second is the password linked to this
 	 * username.
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
 	 * It returns an ArrayList consisting of Course objects and has
 	 * no parameters.
 	 */
 	public ArrayList<Course> getAllCourses() throws IOException, ParseException{
 		ArrayList<FSubjectsResponse> fsr = controller.getAllSubjects();
		
		// The first if statement is used to check if the user has already accessed the page 
 		// for all courses. In that way we do not spend time refilling the array.
 		if (allCourses.size() != fsr.size()) {
 			allCourses.clear();
 			
 			// The following part of the code is used to get the professors from the database
 	 		// This is done, in order to find out the Professor objects who teach each course
 			// (otherwise, only their professorId will be known to each Course object).
 			ArrayList<FProfessorsResponse> fpr = controller.getAllProfessors();
 	 		
 	 		if (allProfessors.size() != fpr.size()) {
 	 			allProfessors.clear();
 	 			
 		 		for (FProfessorsResponse i : fpr)
 		 			allProfessors.add(new Professor(i.name, i.id, i.email, i.profilePhoto, i.phone, i.office, i.rating));
 	 		}
 	 		// allProfessors, now contains all the professors contained in the database
 			
 			for (FSubjectsResponse i : fsr) 
 				allCourses.add(new Course(i.id, i.name, i.associatedProfessors, i.rating, i.semester, allProfessors));
 		}
 		
 		return allCourses;
 	}
 	
 	public Course getCourseById(String courseId) throws IOException, ParseException {
 		for (Course c : getAllCourses()) {
 			if (c.getId().equals(courseId)) {
 				return c;
 			}
 		}
 		
 		return null;
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

 	/*
 	 * Method used by students in order to disenroll from a course.
 	 * The method returns true only if the disenrollment was successful.
 	 * It returns false if the disenrollment failed (student not enrolled)
 	 * or because of the http request failure.
 	 */
 	public boolean courseDisenrollment(String id) throws IOException {
 		return controller.disenrollSubject(id);
 	}
 	
 	/*
 	 * Method used to provide access to the user's enrolled courses.
 	 * It returns an ArrayList consisting of Course objects, which 
 	 * represent the ones that the user is enrolled to and gets no parameters.
 	 */
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
 	
 	/*
 	 * Method used to get the total rating of a selected course, which
 	 * is defined by its id.
 	 * It returns a float object representing the total rating of the selected course
 	 * and gets a String object as parameter which represents the selected course, by its id.
 	 */
 	public float getCourseRating(String id) throws IOException, ParseException {
 		return controller.getSubjectRating(id);
 	}

 	/*
 	 * Method used to get the stars of a selected course, which
 	 * is defined by its id, which a student gave to it.
 	 * It returns a float object representing the stars of the selected course given by the user
 	 * and gets a String object as parameter which represents the selected course, by its id.
 	 */
 	public int getMyCourseRating(String id) throws IOException, ParseException {
 		return controller.getMySubjectRating(id);
 	}
 	
 	/*
 	 * Method used to get the ECTS provided by a course.
 	 * It returns an integer object which represents the course's
 	 * ECTS and gets a Course object as a parameter, which defines 
 	 * the selected course.
 	 */
 	public int getCourseECTS(Course course) {
 		return course.getECTS();
 	}
 	
 	public int getCourseSemester(Course course) {
 		return course.getSemester();
 	}
 	
 	/*
 	 * Method used to get all the professors currently teaching at the University.
 	 * It returns an ArrayList consisting of Professor objects and gets no
 	 * parameters.
 	 */
 	public ArrayList<Professor> getAllProfessors() throws IOException, ParseException{
 		ArrayList<FProfessorsResponse> fpr = controller.getAllProfessors();
 		
 		getAllCourses(); // Fill allCourses, if it is empty. Used for finding the courses each professor teaches.
 		
 		if (allProfessors.size() != fpr.size()) {
 			allProfessors.clear();
 			
	 		for (FProfessorsResponse i : fpr)
	 			allProfessors.add(new Professor(i.name, i.id, i.email, i.profilePhoto, i.phone, i.office, i.rating));
	 		
	 		// This part returns the courses each professor teaches.
	 		for (Professor professor : allProfessors)
	 			professor.getCoursesTaught(allCourses);
 		}
 		
 		return allProfessors;
 	}
 	
 	public Professor getProfessorById(int professorId) throws IOException, ParseException {
 		for (Professor p : getAllProfessors()) {
 			if (p.getProfessorId()==professorId) {
 				return p;
 			}
 		}
 		
 		return null;
 	}
 	
 	// Change comments:!!!!!
 	public boolean rateProfessor(int stars, int professorId) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedProfessor = 0;
 		
 		// Checking if the course the Student chose to rate is attended by him
		for (Professor professor : allProfessors) {
			if (professor.getProfessorId() == professorId) {
					success = controller.setProfessorRating(stars, professorId);
					break;
			}
			
			indexOfRatedProfessor++;
		}
		
		// Update data with new values if rating was successful
 		if (success) {
 			float rating = controller.getProfessorRating(professorId);
 			
 			allProfessors.get(indexOfRatedProfessor).setRating(rating); // Update allCourses		
 		}
 		
 		return success;
 	}
 	
 	public float getProfessorRating(int professorId) throws IOException, ParseException {
 		return controller.getProfessorRating(professorId);
 	}
 	
 	public int getMyProfessorRating(int professorId) throws IOException, ParseException {
 		return controller.getMyProfessorRating(professorId);
 	}
 	
 	/*
 	 * Method used by professors, in order to set an available date for appointments with
 	 * students.
 	 * It returns true, only if the operation and the connection were successful and false
 	 * if the operation failed or the server failed to respond correctly and receives three
 	 * int type variables as parameters (the day, the starting hour and the ending hour, that
 	 * the professor will be available for appointments).
 	 */
 	public boolean setAvailableDate(int day, int startHour, int endHour) throws IOException {
 		return controller.setAvailabilityDates(day, startHour, endHour); 
 	}
 	
 	/*
 	 * Method used for getting the dates that a professor is available for an appointment
 	 * with a student. It uses the professor's unique id in order to locate the professor
 	 * and then checks professor.timeslots is not updated, or has not been filled at all.
 	 * It returns the an ArrayList containing Timeslot objects (the dates that the selected
 	 * professor is available for appointments) and receives an int type variable, representing 
 	 * the professor's unique id.
 	 */
 	public ArrayList<Timeslot> getAvailableTimeslots(String name) throws IOException, ParseException {
 		Professor selectedProfessor = null; // The professor found below
 		Calendar nextAvailableDate = Calendar.getInstance(); // Next date the professor set as available
 		Date availableDate; // For temporary storage and parsing of data to a Date object
 		int weekday; // The Calendar.DAY_OF_WEEK attribute, for the available date
 		int indexOfWeekday = 0; // It's index in the far.dates ArrayList.
 		
 		// Find the professor, from allProfessors ArrayList:
 		for (Professor professor : this.getAllProfessors())
 			if (professor.getDisplayName().equals(name))
 				selectedProfessor = professor;
 		
 		FAvailabilityResponse far = controller.getAvailabilityDates(selectedProfessor.getProfessorId());
 		
 		// Matching the today's day with the correct one from the dates ArrayList:
 		for (HashMap<String, Integer> date : far.dates) {
 			if (date.get("day").equals(nextAvailableDate.get(Calendar.DAY_OF_WEEK))) {
 				weekday = date.get("day");
 				break;
 			}
 			
 			indexOfWeekday++;
 		}
 		
 		// Reform the dates ArrayList, so that weekday is the first element
 		
		// The operation will be done as many times as the element needs to become first
		// In this case, its distance from the first element is that number.
		for (int i = 0; i < indexOfWeekday; i++) {
 			int j = 0;
 			
 			for (int k = 0; k < far.dates.size() - 1; k++) {
 				Collections.swap(far.dates, j, (j + 1) % far.dates.size());
			
 	 			j++;
 			}
		}
 		
 		// It will return available dates for this week and the next 3, meaning that the available
 		// dates are set by professors only once, but can be changed for all the days issued.
		for (HashMap<String, Integer> date : far.dates) {
			nextAvailableDate.set(Calendar.HOUR_OF_DAY, date.get("startHour"));
			nextAvailableDate.set(Calendar.MINUTE, 0);
			nextAvailableDate.set(Calendar.SECOND, 0);
			
			availableDate = nextAvailableDate.getTime();
			selectedProfessor.addAvailableTimeslot((int)(availableDate.getTime() / 1000));
			
			nextAvailableDate.add(Calendar.DAY_OF_YEAR, 1);
		}
 		
 		return selectedProfessor.getAvailableTimeslots();
 	}
 	
 	public ArrayList<Timeslot> getBookedTimeslots(String name) throws IOException, ParseException{
 		Professor selectedProfessor = null;
 		
 		// Check if the professor with this name, already has an active timeslot:
 		for (Professor p : this.getAllProfessors())
 			if (p.getDisplayName().equals(name))
 				selectedProfessor = p;
 		
 		ArrayList<FAppointmentsResponse> far = controller.getMyAppointments();
 		
 		for (FAppointmentsResponse f : far) {
 			selectedProfessor.getBookedTimeslots().add(new Timeslot(f.id, f.studentId, f.professorId, f.dateTimestamp, 
 			f.status, f.created_at));
 		}

 		return selectedProfessor.getBookedTimeslots(); // Error occurred and did not manage to connect to server.
 	}
 	
 	public boolean setDisplayName(String newDisplayName) throws IOException {
 		return controller.setDisplayName(newDisplayName);
 	}
 	
 	public String getDisplayNameStudent() {
 		return student.getDisplayName();
 	}
 	
 	public String getDisplayNameProfessor() {
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
