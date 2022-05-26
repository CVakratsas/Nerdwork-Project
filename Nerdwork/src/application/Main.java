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
		
		System.out.println("\nGet all Subjects and parse into Course: ");
		fsr = controller.getAllSubjects();
		
		for (int i = 0; i < fsr.size(); i++) 
			courses.add(new Course(fsr.get(i).id, fsr.get(i).name, fsr.get(i).rating, fsr.get(i).semester));
		
		int i = 1;
		for (Course course: courses) {
			System.out.println("Course " + i + ": " 
								+ course.getId() + " "
								+ course.getName() + " "
								+ course.getRating() + " "
								+ course.getSemester());
			i++;
		}
		
		courses.clear();
		
		System.out.println("\nRating a Course: ");
		if (controller.setSubjectRating(3, "AIC102")) {
			// Πρέπει να ξανά κάνουμε getAllSubjects, για εμφάνιση αποτελέσματος στο GUI:
			// Σημείωση: Κάθε φορά πρέπει να αδειάζουμε την ArrayList<Course>
			
			fsr = controller.getAllSubjects();
			
			for (i = 0; i < fsr.size(); i++) 
				courses.add(new Course(fsr.get(i).id, fsr.get(i).name, fsr.get(i).rating, fsr.get(i).semester));
			
			i = 1;
			for (Course course: courses) {
				System.out.println("Course " + i + ": " 
									+ course.getId() + " "
									+ course.getRating());
				i++;
			}
			
		}
		
		else 
			System.out.println("You have already rated this course!");
		
		System.out.println("\nEnroll to a Course: ");
		if (controller.enrollSubject("AIC103") || controller.enrollSubject("AIC104"))
			System.out.println("Successful enrollment!");
		else 
			System.out.println("Already enrolled!");
	
		System.out.println("\nGetting enrolled courses: ");
		
		fsr = controller.getAllSubjects();
		
		for (i = 0; i < fsr.size(); i++) 
			courses.add(new Course(fsr.get(i).id, fsr.get(i).name, fsr.get(i).rating, fsr.get(i).semester));
		
		ArrayList<String> enrolledCourseNames = controller.getEnrolledSubjects();
		ArrayList<Course> myCourses = new ArrayList<Course>();
		System.out.println(enrolledCourseNames);
		for (i = 0; i < enrolledCourseNames.size(); i++) 
			for (int j = 0; j < courses.size(); j++)	
				if (enrolledCourseNames.get(i).equals(courses.get(j).getId()))
					myCourses.add(courses.get(j));
		
		i = 0;
		for (Course course: myCourses) {
			System.out.println("Course " + i + ": " 
					+ course.getId() + " "
					+ course.getName() + " "
					+ course.getRating() + " "
					+ course.getSemester());
			i++;
		}
	}
}
