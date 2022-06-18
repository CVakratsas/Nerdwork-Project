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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import application.api.FAppointmentsResponse;
import application.api.FAvailabilityResponse;
import application.api.FLoginResponse;
import application.api.FProfessorsResponse;
import application.api.FSubjectsResponse;
import application.api.FUserInformationResponse;
import application.api.URestController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

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
 	private ArrayList<Professor> allProfessors;
 	private User user; // The logged in user's information.
	public static final String dbURL = "https://nerdnet.geoxhonapps.com/cdn/profPhotos/";
 	public static final String Timezone = "GMT";
 	
 	// Constructor:
 	private GuiController() throws IOException, ParseException {
 		controller = new URestController();
 		allCourses = new ArrayList<Course>();
 		allProfessors = new ArrayList<Professor>();
 	}
 	
 	/*
 	 * Method used to register user.
 	 * In order for the registration to be successful the user needs to pass
 	 * four String arguments: a username, a password, the name to be displayed 
 	 * to other users and an email (his academic email). It returns true if the 
 	 * registration was successful and false otherwise (described below).
 	 */
 	public boolean register(String username, String password, String displayName, String email, int orientation) throws IOException {
 		if (checkPassword(password).equals("correct"))
 			return controller.doRegister(username, password, displayName, email, orientation); // false if email incorrect or username already exists.
 		
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
 		
 		// Call one of the most important methods:
 		getAllProfessors(); // Users keep track of their appointments, though there is no such connection in the database.
 		
 		if (flr.isSuccess) {
	 		if (flr.accountType == 0) {
	 			user = new Student(flr.userId, flr.username, flr.displayName, flr.orientation);
	 			user.setEmail(controller.getUserProfile(user.getUserId()).email);
	 			FUserInformationResponse userProfile = controller.getUserProfile(flr.userId);
	 			String userBio = userProfile.bio;
	 			user.setBio(userBio);
	 		}
	 		else {
	 			user = getProfessorById(flr.associatedProfessorId);
	 			user.setUserName(flr.username);
	 			user.setUserId(flr.userId);
	 			user.setOrientation(2);
	 			user.setBio(controller.getUserProfile(user.getUserId()).bio);
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
		 			allProfessors.add(new Professor(i.name, i.id, i.email, i.profilePhoto, i.phone, i.office, i.rating, 2));
	 		}
	 		// allProfessors, now contains all the professors contained in the database
			
			for (FSubjectsResponse i : fsr) 
				allCourses.add(new Course(i.id, i.name, i.associatedProfessors, i.rating, i.semester, allProfessors, i.orientation));
 		}
 		
 		if (user != null) {
 			if (user.getOrientation() == 2) {
 	 			return allCourses;
 	 		}
 	 		else {
 	 			ArrayList<Course> coursesByOrientation = new ArrayList<>();
 	 			
 	 			for (Course c : allCourses) {
 	 				if (c.getOrientation() == user.getOrientation() || c.getOrientation() == 2) {
 	 					coursesByOrientation.add(c);
 	 				}
 	 			}
 	 			
 	 			return coursesByOrientation;
 	 		}
 		}
 		else {
 			return allCourses;
 		}
 	}
 	
 	/*
 	 * Method used to get a Course object from allCourses ArrayList,
 	 * by its id.
 	 * It returns the course with the id (Integer) provided (as parameter),
 	 * if found in the allCourses ArrayList.
 	 * Note: Professors can not add new Courses in the database.
 	 */
 	public Course getCourseById(String courseId) throws IOException, ParseException {
 		for (Course course : allCourses) {
 			if (course.getId().equals(courseId)) {
 				return course;
 			}
 		}
 		
 		return null;
 	}
 	
 	/*
 	 * Method used by students in order to enroll to a course.
 	 * The method returns true only if the enrollment was successful.
 	 * It returns false if the enrollment failed (student already enrolled)
 	 * or because of the http request failure. It receives a String object,
 	 * as parameter, representing the course's id
 	 */
 	public boolean courseEnrollment(String courseId) throws IOException, ParseException {
 		boolean success = controller.enrollSubject(courseId);
 		
 		if (success)
 			user.addCourse(getCourseById(courseId));
 		
 		return success;
 	}

 	/*
 	 * Method used by students in order to disenroll from a course.
 	 * The method returns true only if the disenrollment was successful.
 	 * It returns false if the disenrollment failed (student not enrolled)
 	 * or because of the http request failure. It receives a String object,
 	 * as parameter, rrepresenting the course's id
 	 */
 	public boolean courseDisenrollment(String courseId) throws IOException, ParseException {
 		boolean success = controller.disenrollSubject(courseId);
 		
 		if (success)
 			user.removeCourse(getCourseById(courseId));
 		
 		return success;
 	}
 	
 	/*
 	 * Method used to provide access to the user's enrolled courses.
 	 * It returns an ArrayList consisting of Course objects, which 
 	 * represent the ones that the user is enrolled to and receives
 	 *  no parameters.
 	 */
 	public ArrayList<Course> getEnrolledCourses() throws IOException, ParseException{
 		ArrayList<String> enrolledCourses = controller.getEnrolledSubjects();
 		
 		// The first if statement is used to check if the user has already accessed the page 
 		// for his courses. In that way we do not spend time gathering data from the database.
 		if (user.getMyCourses().size() != enrolledCourses.size()) {
			user.clearMyCourses();
			
			for (int i = 0; i < enrolledCourses.size(); i++) 
				for (int j = 0; j < allCourses.size(); j++)	
					if (enrolledCourses.get(i).equals(allCourses.get(j).getId()))
						user.addCourse(allCourses.get(j));
 		}
 		
 		return user.getMyCourses();
 	}
 	
 	/*
 	 * Method used for rating a Course, by students. It checks if
 	 * the selected course is attended by the student and also updates 
 	 * attributes with new data.
 	 * Returns true if the rating was successful and false if it was not
 	 * (student already rated this course) or an error occurred on the side
 	 * of the server. It receives an int type parameter (stars given to the course)
 	 * and a Course object (the course being rated), as parameters.
 	 */
 	public boolean rateCourse(int stars, Course selectedCourse) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedCourse = 0;
 		
 		// Checking if the course the Student chose to rate is attended by him
		for (Course course : user.getMyCourses()) {
			if (course.getId().equals(selectedCourse.getId())) {
					success = controller.setSubjectRating(stars, selectedCourse.getId());
					break;
			}
			
			indexOfRatedCourse++;
		}
		
		// Update data with new values if rating was successful
 		if (success) {
 			float rating = controller.getSubjectRating(selectedCourse.getId());
 			
 			user.getMyCourses().get(indexOfRatedCourse).setRating(rating);
 			
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
 	 * Method used to get the total rating of a selected course.
 	 * It returns a float object representing the total rating of the selected course
 	 * and gets a Course object as parameter which represents the selected course.
 	 */
 	public float getCourseRating(Course selectedCourse) throws IOException, ParseException {
 		return controller.getSubjectRating(selectedCourse.getId());
 	}

 	/*
 	 * Method used to get the stars of a selected course, which a student gave to it.
 	 * It returns a float object representing the stars of the selected course given by the user
 	 * and gets a Course object as parameter which represents the selected course.
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
 	public int getCourseECTS(Course selectedCourse) {
 		return selectedCourse.getECTS();
 	}
 	
 	/*
 	 * Method used to get the semester a course belongs to.
 	 * It returns an int type variable (the course's semester)
 	 * and receives a Course object (the selected course).
 	 */
 	public int getCourseSemester(Course selectedCourse) {
 		return selectedCourse.getSemester();
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
	 			allProfessors.add(new Professor(i.name, i.id, i.email, i.profilePhoto, i.phone, i.office, i.rating, 2));
	 		
	 		// This part returns the courses each professor teaches.
	 		for (Professor professor : allProfessors)
	 			professor.getCoursesTaught(allCourses);
 		}
 		
 		return allProfessors;
 	}
 	
 	/*
 	 * Method used to get a professor, from allProfessors list by his id.
 	 * It returns the professor found and receives an type parameter
 	 * representing the professor's id.
 	 */
 	public Professor getProfessorById(int professorId) throws IOException, ParseException {
 		for (Professor professor : allProfessors) {
 			if (professor.getProfessorId() == professorId) {
 				return professor;
 			}
 		}
 		
 		return null;
 	}
 	
 	/*
 	 * Method used to rate a professor, that is selected by a student.
 	 * It returns true if the operation was successful and false otherwise 
 	 * (server failed to respond or student already rated this professor)
 	 * and receives an int type (starts given by the student to the professor) and 
 	 * Professor object (the professor being rated) as parameters.
 	 */
 	public boolean rateProfessor(int stars, Professor selectedProfessor) throws IOException, ParseException {
 		boolean success = false;
 		int indexOfRatedProfessor = 0;
 		
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
 			
 			allProfessors.get(indexOfRatedProfessor).setRating(rating); 		
 		}
 		
 		return success;
 	}
 	
 	/*
 	 * Method used to get the total rating of a professor.
 	 * It returns a float type variable (the professor's total rating)
 	 * and receives a Professor object (the selected professor), as parameter.
 	 */
 	public float getProfessorRating(Professor selectedProfessor) throws IOException, ParseException {
 		return controller.getProfessorRating(selectedProfessor.getProfessorId());
 	}
 	
 	/*
 	 * Method used to get the rating given by the student to a professor.
 	 * It returns a int type variable (the stars given to the professor by the student)
 	 * and receives a Professor object (the selected professor), as parameter.
 	 */
 	public int getMyProfessorRating(Professor selectedProfessor) throws IOException, ParseException {
 		return controller.getMyProfessorRating(selectedProfessor.getProfessorId());
 	}
 	
 	/*
 	 * Method used by professors, in order to set an available date for appointments with
 	 * students.
 	 * It returns true, only if the operation and the connection were successful and false
 	 * if the operation failed or the server failed to respond correctly and receives three
 	 * int type variables as parameters (the day, the starting hour and the ending hour, that
 	 * the professor will be available for appointments).
 	 */
 	public boolean setAvailableTimeslot(int day, int startHour, int endHour) throws IOException, ParseException {
        boolean success = false;
 		
 		if(user instanceof Professor) 
 			success = controller.setAvailabilityDates(day, startHour, endHour);
 				if (success) {
 					Professor professor = (Professor) user;
 	
 					getAvailableTimeslots(getProfessorById(professor.getProfessorId()));
 				
 				}
 					
        return success;
    }
 	/*
 	 * Method used for getting the dates that a professor is available for an appointment
 	 * with a student. It uses the professor's unique id in order to locate the professor.
 	 * It returns an ArrayList containing Timeslot objects (the dates that the selected
 	 * professor is available for appointments) and receives a Professor object, representing 
 	 * the selected professor.
 	 */
 	public ArrayList<Timeslot> getAvailableTimeslots(Professor selectedProfessor) throws IOException, ParseException {
 		ArrayList<Timeslot> requested = getRequestedAppointments();
 		ArrayList<Timeslot> reserved = getReservedTimeslots(selectedProfessor);
 		Calendar nextAvailableDate = Calendar.getInstance(TimeZone.getTimeZone(Timezone)); // Next date the professor set as available
 		Date availableDateStart; // For temporary storage and parsing of data to a Date object
 		Date availableDateEnd;
		int i = 0;
		boolean firstAvailableDayOfWeekFound = false; // This is set to true only when the Calendar instance matches with a day contained in far.dates.
		
 		FAvailabilityResponse far = controller.getAvailabilityDates(selectedProfessor.getProfessorId());
 		
 		if (far.dates.isEmpty())
 			return null;
	
		allProfessors.get(selectedProfessor.getProfessorId() - 1).clearTimeslots();
		
		// Here the Integer data, will be converted into a date timestamp, with the
		// help of the the Calendar and Date Java classes and will be used to create
		// available Timeslot objects
		
		while (i < 7) {
			for (HashMap<String, Integer> date : far.dates) {
				if (nextAvailableDate.get(Calendar.DAY_OF_WEEK) - 1 == date.get("day")) {
					
					firstAvailableDayOfWeekFound = true;
					
					nextAvailableDate.set(Calendar.HOUR_OF_DAY, date.get("startHour"));
					nextAvailableDate.set(Calendar.MINUTE, 0);
					nextAvailableDate.set(Calendar.SECOND, 0);
					nextAvailableDate.set(Calendar.MILLISECOND, 0);
					
					availableDateStart = nextAvailableDate.getTime();

					nextAvailableDate.set(Calendar.HOUR_OF_DAY, date.get("endHour"));
					nextAvailableDate.set(Calendar.MINUTE, 0);
					nextAvailableDate.set(Calendar.SECOND, 0);
					nextAvailableDate.set(Calendar.MILLISECOND, 0);

					availableDateEnd = nextAvailableDate.getTime();

					selectedProfessor.addTimeslot(availableDateStart.getTime(), availableDateEnd.getTime(), requested, reserved);
				}
			}
			
			if (firstAvailableDayOfWeekFound)
				i++;
			
			nextAvailableDate.add(Calendar.DAY_OF_YEAR, 1);
		}
 	
		allProfessors.set((selectedProfessor.getProfessorId() - 1), selectedProfessor);
		
 		return selectedProfessor.getTimeslots();
 	}
 	
 	public boolean requestAppointment(Professor selectedProfessor, Timeslot timeslot) throws IOException, ParseException {
	 	ArrayList<Timeslot> requestedAppointments = getRequestedAppointments();
	 	boolean success = false;
 		
 		if (user instanceof Student && timeslot.getStatus() == 3) {
	 		success = controller.bookAppointment(selectedProfessor.getProfessorId(), timeslot.getStartHourTimestamp());
	 		
	 		// Check if server responded correctly
	 		if (success) 
	 			for (Timeslot requested : requestedAppointments)
	 				if (requested.getProfessorId() == selectedProfessor.getProfessorId()) 
	 					success = false; // Student already requested.
	 		
	 		// Check if already requested appointment with this professor.
	 		if (success)
	 			selectedProfessor.addRequestedAppointment(timeslot);
	 	}

 		return false;
 	}
 	
 	/*
 	 * Method used to get the user's requests for appointments (professor)/requested appointments (student)
 	 * It returns an ArrayList consisting of Timeslot objects, representing the requested appointments
 	 * and receives no parameters.
 	 */
 	public ArrayList<Timeslot> getRequestedAppointments() throws IOException, ParseException {
 		ArrayList<FAppointmentsResponse> far = controller.getMyAppointments();
 		
 		user.clearRequestedAppointments();
 		
 		for (FAppointmentsResponse timeslotParser : far) 
 			if (timeslotParser.status == 0)
 				user.addRequestedAppointment(new Timeslot(timeslotParser.id, timeslotParser.studentId, timeslotParser.professorId, timeslotParser.dateTimestamp, (timeslotParser.dateTimestamp + 1800), timeslotParser.status, timeslotParser.created_at));
 		
 		return user.getRequestedAppointments();
 	}
 	
 	/*
 	 * Method used by professor users to accept a request for appointment.
 	 * It returns true if the operation was successful and the server responded 
 	 * correctly and false if the operation failed or the server did not 
 	 * respond correctly. It receives a Timeslot object (the request of appointment),
 	 * as parameter.
 	 */
 	public boolean acceptAppointmentRequest(Timeslot requestedAppointment) throws IOException {
 		if (user instanceof Professor)
 			return controller.acceptAppointment(requestedAppointment.getId());
 		
 		return false;
 	}
 	
 	/*
 	 * Method used by users to reject/cancel a request for appointment.
 	 * It returns true if the operation was successful and the server responded 
 	 * correctly and false if the operation failed or the server did not 
 	 * respond correctly. It receives a Timeslot object (the request of appointment),
 	 * as parameter.
 	 */
 	public boolean rejectAppointmentRequested(Timeslot requestedAppointment) throws IOException {
 		return controller.cancelAppointment(requestedAppointment.getId());
 	}
 	
 	/*
 	 * Method used to get the appointments that have been reserved by students
 	 * with a professor.
 	 * It returns an ArrayList consisting of Timeslot objects representing the
 	 * reserved appointments and receives a Professor object (the selected professor)
 	 * as parameter
 	 */
 	public ArrayList<Timeslot> getReservedTimeslots(Professor selectedProfessor) throws IOException, ParseException{
 		ArrayList<Integer> reservedStartingTimestamps = controller.getBookedTimestamps(selectedProfessor.getProfessorId());
 		
 		selectedProfessor.clearReservedAppointments();
 		
 		for (Integer timestamp : reservedStartingTimestamps) {
 			selectedProfessor.addReservedAppointment(new Timeslot(timestamp, timestamp + 1800, 1));
 		}
 		
 		return selectedProfessor.getReservedAppointments();
 	}
 	
 	/*
 	 * Method used to change a users display name.
 	 * It returns true if the operation was successful and the server responded correctly
 	 * and false if the the operation failed or the server did not respond correctly.
 	 * It receives a String object as parameter (the new display name).
 	 */
 	public boolean setDisplayName(String newDisplayName) throws IOException {
 		boolean success = controller.setDisplayName(newDisplayName);
 		
 		if (success)
 			user.setDisplayName(newDisplayName);
 		
 		return success;
 	}
 	
 	/*
 	 *Method used to set a Bio for the user 
 	 * It returns true if the operation was successful and the server responded correctly
 	 * and false if the the operation failed or the server did not respond correctly.
 	 * It receives a String object as parameter (the new bio).
 	 * */
 	
 	public boolean setBio(String bio) throws IOException {
 		if (bio.length() <= 300)
 			if (controller.setBio(bio)) {
 				user.setBio(bio);
 				
 				return true;
 			}
 		
 		return false;
 	}
 	
 	/*
 	 * Method used to change a user's password.
 	 * It returns a String object representing the success or not
 	 * and receives two String objects as parameters (the new and 
 	 * old passwords).
 	 */
	public String changePassword(String oldPassword, String newPassword) throws IOException {
 		String isCorrect = "correct";
 		String message = checkPassword(newPassword);
 		
 		if (message.equals(isCorrect)) {
 			if (controller.setPassword(oldPassword, newPassword))
 				return "Ξ� ΞΊΟ‰Ξ΄ΞΉΞΊΟ�Ο‚ ΞµΞ½Ξ·ΞΌΞµΟ�Ο�ΞΈΞ·ΞΊΞµ ΞµΟ€ΞΉΟ„Ο…Ο‡Ο�Ο‚!";
 			
 			else
 				return "Ξ� Ο€Ξ±Ξ»ΞΉΟ�Ο‚ ΞΊΟ‰Ξ΄ΞΉΞΊΟ�Ο‚ Ξ΄ΞµΞ½ ΞµΞ―Ξ½Ξ±ΞΉ Ξ­Ξ³ΞΊΟ…Ο�ΞΏΟ‚!";
 		}
 		
 		return message;
 	}
 
	public User getUser() {
		return user;
	}
	
	/*
	 * Method for checking if the inputed password has been written according to our password pattern.
	 * checkPassword, receives a String object (new password inputed by the user), as a 
	 * parameter and returns a String object representing the success or not of the operation.
	 * Some of the following code is product of others. Sources follow:
	 * 
	 * Code for password checking found here: https://stackoverflow.com/a/41697673 and modified
	 * to check if there is at least one upper and one lower letter
	 * Pattern documentation: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
	 */
 	public String checkPassword(String newPassword) {
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
 			    
 			String passwordErrors = "Ξ� Ξ½Ξ­ΞΏΟ‚ ΞΊΟ‰Ξ΄ΞΉΞΊΟ�Ο‚ ΞΈΞ± Ο€Ο�Ξ­Ο€ΞµΞΉ Ξ½Ξ± Ο€ΞµΟ�ΞΉΞ­Ο‡ΞµΞΉ:";
 			boolean validPassword = true;
 			    
 			if (!hasUpperLetter.find()) {
 				passwordErrors += "\n- Ο„ΞΏΟ…Ξ»Ξ¬Ο‡ΞΉΟƒΟ„ΞΏΞ½ Ξ­Ξ½Ξ± ΞΊΞµΟ†Ξ±Ξ»Ξ±Ξ―ΞΏ Ο‡Ξ±Ο�Ξ±ΞΊΟ„Ξ®Ο�Ξ±";
 			  	validPassword = false;
 			}
 			if (!hasLowerLetter.find()) {
 				passwordErrors += "\n- Ο„ΞΏΟ…Ξ»Ξ¬Ο‡ΞΉΟƒΟ„ΞΏΞ½ Ξ­Ξ½Ξ± Ο€ΞµΞ¶Ο� Ο‡Ξ±Ο�Ξ±ΞΊΟ„Ξ®Ο�Ξ±";
 			   	validPassword = false;
 			}
 			if (!hasDigit.find()) {
 				passwordErrors += "\n- Ο„ΞΏΟ…Ξ»Ξ¬Ο‡ΞΉΟƒΟ„ΞΏΞ½ Ξ­Ξ½Ξ± Ο�Ξ·Ο†Ξ―ΞΏ";
 			   	validPassword = false;
 			}
 		    if (!hasSpecial.find()) {
 		    	passwordErrors += "\n- Ο„ΞΏΟ…Ξ»Ξ¬Ο‡ΞΉΟƒΟ„ΞΏΞ½ Ξ­Ξ½Ξ±Ξ½ ΞµΞΉΞ΄ΞΉΞΊΟ� Ο‡Ξ±Ο�Ξ±ΞΊΟ„Ξ®Ο�Ξ±";
 		    	validPassword = false;
 		    }
 		    if (validPassword)
 		    	return "correct"; 
 		    else
 		    	return passwordErrors;
 		}
 		else {
 			return "Ξ� Ξ½Ξ­ΞΏΟ‚ ΞΊΟ‰Ξ΄ΞΉΞΊΟ�Ο‚ Ο€Ο�Ξ­Ο€ΞµΞΉ Ξ½Ξ± Ξ­Ο‡ΞµΞΉ ΞΌΞ­Ξ³ΞµΞΈΞΏΟ‚ Ο„ΞΏΟ…Ξ»Ξ¬Ο‡ΞΉΟƒΟ„ΞΏΞ½ 8 Ο‡Ξ±Ο�Ξ±ΞΊΟ„Ξ®Ο�Ο‰Ξ½!";
 		}
 	}
 	
 	public void alertFactory(String header, String content) {
 		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(null);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
 	}
}