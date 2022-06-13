/*
 * Class used in order to connect the GUI part of the program
 * with the database and vice versa. It provides methods for 
 * communication between both and also parses data so that they
 * can be used correctly by both. 
 * 
 * Note: This class is to be used instead of URestController, 
 * while the last must not be used anywhere else in the program
 */

package application.functionality;

import application.api.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;
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
 	private int accountTypeLoggedIn;
 	
 	// Constructor:
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
 		if (checkPassword(password).equals("correct"))
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
 		accountTypeLoggedIn = flr.accountType;
 		
 		if (flr.isSuccess) {
	 		if (accountTypeLoggedIn == 0) {
	 			professor = null;
	 			student = new Student(flr.userId, flr.username, flr.displayName);
	 			student.setEmail(controller.getUserProfile(student.getUserId()).email);
	 		}
	 		else {
	 			student = null;
	 			professor = new Professor(flr.userId, flr.username, flr.displayName, flr.associatedProfessorId);
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
 	public boolean rateCourse(int stars, Course selectedCourse) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedCourse = 0;
 		
 		// Checking if the course the Student chose to rate is attended by him
		for (Course course : myCourses) {
			if (course.getId().equals(selectedCourse.getId())) {
					success = controller.setSubjectRating(stars, selectedCourse.getId());
					break;
			}
			
			indexOfRatedCourse++;
		}
		
		// Update data with new values if rating was successful
 		if (success) {
 			float rating = controller.getSubjectRating(selectedCourse.getId());
 			
 			myCourses.get(indexOfRatedCourse).setRating(rating); // Update allCourses
 			
 			// Update myCourses
 			for (int i = 0; i < allCourses.size(); i++) {
 				if (allCourses.get(i).getId().equals(selectedCourse.getId())) {
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
 	public float getCourseRating(Course selectedCourse) throws IOException, ParseException {
 		return controller.getSubjectRating(selectedCourse.getId());
 	}

 	/*
 	 * Method used to get the stars of a selected course, which
 	 * is defined by its id, which a student gave to it.
 	 * It returns a float object representing the stars of the selected course given by the user
 	 * and gets a String object as parameter which represents the selected course, by its id.
 	 */
 	public int getMyCourseRating(Course selectedCourse) throws IOException, ParseException {
 		return controller.getMySubjectRating(selectedCourse.getId());
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
 			if (p.getProfessorId() == professorId) {
 				return p;
 			}
 		}
 		
 		return null;
 	}
 	
 	// Change comments:!!!!!
 	public boolean rateProfessor(int stars, Professor selectedProfessor) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedProfessor = 0;
 		
 		// Checking if the course the Student chose to rate is attended by him
		for (Professor professor : allProfessors) {
			if (professor.getProfessorId() == selectedProfessor.getProfessorId()) {
					success = controller.setProfessorRating(stars, selectedProfessor.getProfessorId());
					break;
			}
			
			indexOfRatedProfessor++;
		}
		
		// Update data with new values if rating was successful
 		if (success) {
 			float rating = controller.getProfessorRating(selectedProfessor.getProfessorId());
 			
 			allProfessors.get(indexOfRatedProfessor).setRating(rating); // Update allCourses		
 		}
 		
 		return success;
 	}
 	
 	public float getProfessorRating(Professor selectedProfessor) throws IOException, ParseException {
 		return controller.getProfessorRating(selectedProfessor.getProfessorId());
 	}
 	
 	public int getMyProfessorRating(Professor selectedProfessor) throws IOException, ParseException {
 		return controller.getMyProfessorRating(selectedProfessor.getProfessorId());
 	}
 	
 	public Timeslot getAvailableTimeslotById(int timeslotId) throws IOException, ParseException {
 		for (Professor professor : getAllProfessors())
 			for (Timeslot availableTimeslot : professor.getAvailableTimeslots())
 				if (availableTimeslot.getId() == timeslotId)
 					return availableTimeslot;
 		
 		return null;
 	}
 	
 	public Timeslot getReservedTimeslotById(int timeslotId) throws IOException, ParseException {
		for (Professor professor : getAllProfessors())
 			for (Timeslot reservedTimeslot : professor.getReservedTimeslots())
 				if (reservedTimeslot.getId() == timeslotId)
 					return reservedTimeslot;
 		
 		return null;
 	}
 	
 	/*
 	 * Method used by professors, in order to set an available date for appointments with
 	 * students.
 	 * It returns true, only if the operation and the connection were successful and false
 	 * if the operation failed or the server failed to respond correctly and receives three
 	 * int type variables as parameters (the day, the starting hour and the ending hour, that
 	 * the professor will be available for appointments).
 	 */
 	public boolean setAvailableTimeslot(int day, int startHour, int endHour) throws IOException {
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
 	public ArrayList<Timeslot> getAvailableTimeslots(Professor selectedProfessor) throws IOException, ParseException {
 		Calendar nextAvailableDate = Calendar.getInstance(); // Next date the professor set as available
 		Date availableDateStart; // For temporary storage and parsing of data to a Date object
 		Date availableDateEnd;
 		int weekday; // The Calendar.DAY_OF_WEEK attribute, for the available date
 		int indexOfWeekday = 0; // It's index in the far.dates ArrayList.

 		FAvailabilityResponse far = controller.getAvailabilityDates(selectedProfessor.getProfessorId());
		
 		if (far.dates.isEmpty())
 			return null;
 		
	 	// Here we fill the far.dates with all the unavailable dates. This is done 
	 	// in order to more easily get the distances between two days (though 
	 	// we do not calculate distances. We just move on to the next available date, when 
	 	// we spot an unavailable).
		if (far.dates.size() < 7) {	
	 		for (int i = 0; i < far.dates.size(); i++) {
				if (i < far.dates.size() - 1) {
					if ((far.dates.get(i + 1).get("day") - far.dates.get(i).get("day")) > 1) {
						for (int j = far.dates.get(i).get("day") + 1; j < far.dates.get(i + 1).get("day"); j++) {
							HashMap<String, Integer> unavailableDay = new HashMap<String, Integer>();
							
							i++;
							
							unavailableDay.put("unavailableDay", j);
							far.dates.add(i, unavailableDay);
						}
					}
				}
				// Used to check the first and last elements of far.dates, to see if they match 
				// to the first and last day of the week (starting from 0 as Sunday)
				else {
					if (far.dates.get(0).get("day") > 0)
						for (int j = 0; j < far.dates.get(j).get("day"); j++) {
							HashMap<String, Integer> unavailableDay = new HashMap<String, Integer>();
							
							unavailableDay.put("unavailableDay", j);
							far.dates.add(j, unavailableDay);
						}

					if (far.dates.get(far.dates.size() - 1).get("day") < 6)
						for (int j = far.dates.size(); j <= 6; j++) {
							HashMap<String, Integer> unavailableDay = new HashMap<String, Integer>();
							
							unavailableDay.put("unavailableDay", j);
							far.dates.add(j, unavailableDay);
						}
					
					break;
				}
			}
		}
		
		// Matching the today's day with the correct one from the dates ArrayList:
	 	while (true) {	
 			for (HashMap<String, Integer> date : far.dates) {
		 		if (!date.keySet().contains("unavailableDay"))	
 					if (date.get("day").equals(nextAvailableDate.get(Calendar.DAY_OF_WEEK) - 1)) {
		 				weekday = date.get("day");
		 				break;
		 			}
	 			
	 			indexOfWeekday++;
	 		}
 			
 			// The condition below is used to check if the professor is not available for all days 
 			// of the week. In that case we may not be able to find a day of the week that is truly contained
 			// in far.dates, so we run the loop again, until indexOfWeekday, actually finds a matching day in far.dates.
 			// Found current day or the nearest one to the current day
 			if (indexOfWeekday < far.dates.size())
 				break;
 			
 			// Next day, that may match one of the days in far.dates
 			nextAvailableDate.add(Calendar.DAY_OF_YEAR, 1);
 			indexOfWeekday = 0;
	 	}
	 	
	 	// Reset the calendar object.
	 	
		// Reform the dates ArrayList, so that weekday is the first element
	 	// We do this by making a copy of far.dates, which will have as first 
	 	// element, the element at index indexOfWeekday, in the far.dates. Its
	 	// second element will be the far.dates.get((indexOfWeekday + 1) % 7), so
	 	// that we can get appointments from today utnil the next week.
		ArrayList<HashMap<String, Integer>> farDatesCopy = new ArrayList<HashMap<String, Integer>>();
		
		for (int i = 0; i < far.dates.size(); i++) {
			HashMap<String, Integer> farDatesElement = new HashMap<String, Integer>();
			
			for (String key : far.dates.get(indexOfWeekday).keySet()) 
				farDatesElement.put(key, far.dates.get(indexOfWeekday).get(key));
		
			farDatesCopy.add(farDatesElement);
			
			indexOfWeekday = (indexOfWeekday + 1) % 7; // Keep a logical flow of weekdays.
		}
	
		// Here the Integer data, will be converted into a date timestamp, with the
		// help of the the Calendar and Date Java classes and will be used to create
		// available Timeslot objects
		for (HashMap<String, Integer> date : farDatesCopy) {			
			
			if (!date.keySet().contains("unavailableDay")) {
				
				nextAvailableDate.set(Calendar.HOUR_OF_DAY, date.get("startHour"));
				nextAvailableDate.set(Calendar.MINUTE, 0);
				nextAvailableDate.set(Calendar.SECOND, 0);
	
				availableDateStart = nextAvailableDate.getTime();
				
				nextAvailableDate.set(Calendar.HOUR_OF_DAY, date.get("endHour"));
				nextAvailableDate.set(Calendar.MINUTE, 0);
				nextAvailableDate.set(Calendar.SECOND, 0);
				
				availableDateEnd = nextAvailableDate.getTime();
				
				selectedProfessor.addAvailableTimeslot((int)(availableDateStart.getTime() / 1000), (int)(availableDateEnd.getTime() / 1000));
			}			
			
			nextAvailableDate.add(Calendar.DAY_OF_YEAR, 1);
		}
 		
 		return selectedProfessor.getAvailableTimeslots();
 	}
 	
 	public boolean requestAppointment(Professor professor, int month, int day, int hour, int minutes) throws IOException {
 		Calendar appointmentDate = Calendar.getInstance();
 		
 		appointmentDate.set(Calendar.MONTH, month - 1);
 		appointmentDate.set(Calendar.DAY_OF_MONTH, day);
 		appointmentDate.set(Calendar.HOUR_OF_DAY, hour);
 		appointmentDate.set(Calendar.MINUTE, minutes);
 		appointmentDate.set(Calendar.SECOND, 0);
 		appointmentDate.set(Calendar.MILLISECOND, 0);
 		
 		Date appointmentTimestamp = appointmentDate.getTime();
 		
 		return controller.bookAppointment(professor.getProfessorId(), (int)(appointmentTimestamp.getTime() / 1000));
 	}
 	
 	public ArrayList<Timeslot> getRequestedAppointments() throws IOException, ParseException {
 		Calendar nextRequestedDate = Calendar.getInstance();
 		Date requestedDate;
 		User user;
 		
 		if (accountTypeLoggedIn == 0) {
 			user = this.student;
 		}
 		else {
 			user = this.professor;
 		}
 		
 		ArrayList<FAppointmentsResponse> far = controller.getMyAppointments();
 		
 		for (FAppointmentsResponse timeslotParser : far) {
 			user.addRequestedAppointment(new Timeslot(timeslotParser.id, timeslotParser.studentId, timeslotParser.professorId, timeslotParser.dateTimestamp, (timeslotParser.dateTimestamp + 1800), timeslotParser.status, timeslotParser.created_at));
 		}
 		
 		return user.getRequestedTimelsots();
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
 	
	public String changePassword(String oldPassword, String newPassword) throws IOException {
 		String isCorrect = "correct";
 		String message = checkPassword(newPassword);
 		if (message.equals(isCorrect)) {
 			if (controller.setPassword(oldPassword, newPassword))
 				return "Password updated successfully!";
 			else
 				return "Old password is invalid";
 		}
 		
 		return message;
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
 	private String checkPassword(String newPassword) {
 		// Password verification begins here:
 		if(newPassword.length()>=8) {
 			Pattern upperLetter = Pattern.compile("[A-Z]");
 			Pattern lowerLetter = Pattern.compile("[a-z]");
 			Pattern digit = Pattern.compile("[0-9]");
 			Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}~-]");
 			    
 			// The above code serves as our password pattern 
 			Matcher hasUpperLetter = upperLetter.matcher(newPassword);
 			Matcher hasLowerLetter = lowerLetter.matcher(newPassword);
 			Matcher hasDigit = digit.matcher(newPassword);
 			Matcher hasSpecial = special.matcher(newPassword);
 			    
 			String passwordErrors = "New password must also contain:";
 			boolean validPassword = true;
 			    
 			if (!hasUpperLetter.find()) {
 			  	passwordErrors += "\n- at least one upper case letter";
 			  	validPassword = false;
 			}
 			if (!hasLowerLetter.find()) {
 			   	passwordErrors += "\n- at least one lower case letter";
 			   	validPassword = false;
 			}
 			if (!hasDigit.find()) {
 			   	passwordErrors += "\n- at least one digit number";
 			   	validPassword = false;
 			}
 		    if (!hasSpecial.find()) {
 		    	passwordErrors += "\n- at least one special character";
 		    	validPassword = false;
 		    }
 		    if (validPassword)
 		    	return "correct"; 
 		    else
 		    	return passwordErrors;
 		}
 		else {
 			return "New password must be at least 8 characters long!";
 		}
 	}
}
