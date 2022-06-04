package application;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class Main  {
	
	public static void main(String[] args) throws IOException, ParseException {
		URestController controller = new URestController();
		ArrayList<FSubjectsResponse> fsr;
		ArrayList<Course> courses = new ArrayList<Course>();
		
		//System.out.println(controller.doRegister("iss1234", "123456789", "iss1234", "iss1234@uom.edu.gr"));
		
		System.out.println("Login: ");
		System.out.println(controller.doLogin("iss1234", "123456789"));
		
		System.out.println("\nAppointment Tests: \n");
	}
}
