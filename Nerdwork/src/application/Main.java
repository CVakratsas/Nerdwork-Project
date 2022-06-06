package application;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class Main  {
	
	public static void main(String[] args) throws IOException, ParseException {
		//GUI Controller creation
		GuiController controller = new GuiController();
		
		//Login section
		System.out.print("Login: ");
		System.out.println(controller.login("iss1234", "123456789"));
		System.out.println("--------------------");

		//All courses section
		System.out.println("All courses in the database: ");
		for (Course c : controller.getAllCourses()) {
			System.out.println();
			System.out.println("Id: " + c.getId() + ", " + "Name: " + c.getName() + ", " + "Semester: " + c.getSemester()
			+ ", " + "ECTS: " + c.getECTS() + ", " + "Rating: " + c.getRating());
		}
		System.out.println("--------------------");
		
		//Enrolled courses section
		System.out.println("My enrolled courses: ");
		for (Course c : controller.getEnrolledCourses()) {
			System.out.println();
			System.out.println("Id: " + c.getId() + ", " + "Name: " + c.getName() + ", " + "Semester: " + c.getSemester()
			+ ", " + "ECTS: " + c.getECTS() + ", " + "Rating: " + c.getRating() + ", " + "My rating: " + controller.getMyCourseRating(c.getId()));
		}
		System.out.println("--------------------");
		
		//Enroll to a course
		System.out.println("Enroll to a course (AIC106): ");
		System.out.println("Enrolling to AIC106: " + controller.courseEnrollment("AIC106"));
		System.out.println("My NEW enrolled courses (AIC106 included): ");
		for (Course c : controller.getEnrolledCourses()) {
			System.out.println();
			System.out.println("Id: " + c.getId() + ", " + "Name: " + c.getName() + ", " + "Semester: " + c.getSemester()
			+ ", " + "ECTS: " + c.getECTS() + ", " + "Rating: " + c.getRating() + ", " + "My rating: " + controller.getMyCourseRating(c.getId()));
		}
		System.out.println("--------------------");
		
		//Disenroll from a course
		System.out.println("Disenroll to a course (AIC106): ");
		System.out.println("Disenrolling to AIC106: " + controller.courseDisenrollment("AIC106"));
		System.out.println("My NEW enrolled courses (AIC106 NOT included): ");
		for (Course c : controller.getEnrolledCourses()) {
			System.out.println();
			System.out.println("Id: " + c.getId() + ", " + "Name: " + c.getName() + ", " + "Semester: " + c.getSemester()
			+ ", " + "ECTS: " + c.getECTS() + ", " + "Rating: " + c.getRating() + ", " + "My rating: " + controller.getMyCourseRating(c.getId()));
		}
		System.out.println("--------------------");
		
		//All professors section
		System.out.println("All professors in the database: ");
		for (Professor p : controller.getAllProfessors()) {
			System.out.println();
			System.out.println("ProfId: " + p.getProfessorId() + ", " + "UserId: " + p.getUserId() + ", " +"Name: " + p.getDisplayName() + ", "
			+ "Email: " + p.getEmail() + ", " + "Office: " + p.getOffice() + ", " + "Phone: " + p.getPhone() + ", " 
			+ "Photo: " + p.getProfilePhoto() + ", " + "Rating: " + p.getRating() + ", " + "My rating: " + controller.getMyProfessorRating(p.getProfessorId()));
		}
		System.out.println("--------------------");
		
		//Rate a professor
		System.out.println("Rating professor 14 (achat), 4 stars: ");
		System.out.println("This should return false in case I have already rated professor 14: " + controller.rateProfessor(4, 14));
		System.out.println("Professor 14 rating, my rating:");
		System.out.println(controller.getAllProfessors().get(13).getDisplayName() + ", " + "Rating: "
		+ controller.getAllProfessors().get(13).getRating() + ", " + "My rating: " + controller.getMyProfessorRating(14));
		System.out.println("--------------------");
		
		//To be continued...
		
		System.out.println(controller.getAllProfessors().get(13).getDisplayName());
		System.out.println(controller.getAllProfessors().get(13).getCoursesTaught(controller.getAllCourses()));
		
		/*System.out.println(controller.getAllCourses().get(5).getName());
		for (Professor p : controller.getAllCourses().get(5).getProfessors()) {
			System.out.println(p.getDisplayName());
		}*/
	}
}
