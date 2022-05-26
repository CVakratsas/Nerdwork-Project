package application;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class GuiController {
	
	// Class attributes
	private URestController controller;
	private ArrayList<FSubjectsResponse> fsr;
 	public ArrayList<Course> allCourses;
 	public ArrayList<Course> myCourses;
 	public Student student;
 	public Professor professor;
 	
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
 		return controller.doRegister(username, password, displayName, email);
 	}
 	
 	public boolean login(String username, String password) throws IOException, ParseException {
 		FLoginResponse flr;
 		
 		flr = controller.doLogin(username, password);
 		
 		if (flr.isSuccess) {
	 		if (flr.accountType == 0) {
	 			professor = null;
	 			student = new Student();
	 			
	 			return true;
	 		}
	 		else {
	 			student = null;
	 			professor = new Professor();
	 			
	 			return true;
	 		}
 		}
 		else 
 			return false;
 	}
}
