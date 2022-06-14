package application.functionality;

import application.api.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

public class Main  {
	
	public static void main(String[] args) throws IOException, ParseException {
		//GUI Controller creation
		GuiController controller = GuiController.getInstance();
		
		//Login section
		// "probatos", "beeeH1234@" Student
		// "arnis", "123456789Ab!" Student2
		// "example1", "123456789Ab!" Professor Manos Roumeliotis
		//System.out.println(controller.register("arnis", "123456789Ab!", "Arnakis", "arnis@uom.edu.gr"));
		System.out.print("Login: ");
		System.out.println(controller.login("example1", "123456789Ab!"));
		System.out.println("--------------------");
		
		System.out.println(controller.getAllProfessors().get(8).getProfessorId());
		System.out.println("--------------------");
		/*
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
			System.out.println("ProfId: " + p.getUserName() + ", " + "UserId: " + p.getUserId() + ", " +"Name: " + p.getDisplayName() + ", "
			+ "Email: " + p.getEmail() + ", " + "Office: " + p.getOffice() + ", " + "Phone: " + p.getPhone() + ", " 
			+ "Photo: " + p.getProfilePhoto() + ", " + "Rating: " + p.getRating() + ", " + "My rating: " + controller.getMyProfessorRating(p));
		}
		System.out.println("--------------------");
		
		//Rate a professor
		System.out.println("Rating professor 14 (achat), 4 stars: ");
		System.out.println("This should return false in case I have already rated professor 14: " + controller.rateProfessor(4, 14));
		System.out.println("Professor 14 rating, my rating:");
		System.out.println(controller.getAllProfessors().get(13).getDisplayName() + ", " + "Rating: "
		+ controller.getAllProfessors().get(13).getRating() + ", " + "My rating: " + controller.getMyProfessorRating(14));
		System.out.println("--------------------");
		*/
		//To be continued...
		
		// Setting new available dates for professor Aλεξανδροπούλου Ευγενία
//		System.out.println("Attempt a set of available date: " + controller.setAvailableTimeslot(2, 11, 13));
//		System.out.println("Change previous available date: " + controller.setAvailableTimeslot(2, 16, 18)); // I don't know if it changes, since it encounters problems with connecting to the get available dates side of the api.
//		
//		controller.setAvailableTimeslot(3, 12, 15);
//		controller.setAvailableTimeslot(5, 12, 16);
//		System.out.println("--------------------");
		
		
//		// Use of getAvailableTimeslots
//		ArrayList<Timeslot> timelsotsAvailable = controller.getAvailableTimeslots(controller.getAllProfessors().get(8));
//		ArrayList<HashMap<String, Date>> appointmentsAvailable = new ArrayList<>();
//		
//		// Here it returns all the available appointments:
//		for (Timeslot t : timelsotsAvailable) {
//			appointmentsAvailable = t.getAvailableAppointments();
//		
//			// Here it returns the date data in a printable for our purposes way
//			// Note: it only returns the day of the month, the start and end hour of the appointment:
//			ArrayList<HashMap<String, Integer>> appointmentTimeslotsToBePresentedInGui = new ArrayList<HashMap<String, Integer>>();
//	
//			for (HashMap<String, Date> appointmentAvailable : appointmentsAvailable) {
//				HashMap<String, Integer> element;
//				
//				element = Timeslot.getDateInfo(appointmentAvailable.get("startHour"));
//				appointmentTimeslotsToBePresentedInGui.add(element);
//				
//				element = Timeslot.getDateInfo(appointmentAvailable.get("endHour"));
//				appointmentTimeslotsToBePresentedInGui.add(element);
//			}
//			
//			// Print them:
//			for (int i = 0 ; i < appointmentTimeslotsToBePresentedInGui.size() - 1; i += 2) {
//				System.out.print("Day: " + appointmentTimeslotsToBePresentedInGui.get(i).get("day") + "/" + appointmentTimeslotsToBePresentedInGui.get(i).get("month"));
//				System.out.println(" starting at: " + appointmentTimeslotsToBePresentedInGui.get(i).get("hour") + ":" + appointmentTimeslotsToBePresentedInGui.get(i).get("minutes") + " and ending at: " + appointmentTimeslotsToBePresentedInGui.get(i + 1).get("hour") + ":" + appointmentTimeslotsToBePresentedInGui.get(i + 1).get("minutes"));
//			}
//		
//			System.out.println("\n\n");
//		}
		
		// Request an appointment from professor 0.
		//System.out.println(controller.requestAppointment(controller.getProfessorById(9), 6, 17, 14, 30));
		
		
		ArrayList<Timeslot> available = controller.getAvailableTimeslots(controller.getAllProfessors().get(8));
		
		for (Timeslot t : available) {
			HashMap<String, Date> element = t.getAppointment();
			HashMap<String, Integer> startDatePrint = Timeslot.getDateInfo(element.get("startHour"));
			HashMap<String, Integer> endDatePrint = Timeslot.getDateInfo(element.get("endHour"));
			
			System.out.print("Day: " + startDatePrint.get("day") + "/" + startDatePrint.get("month"));
			System.out.println(" starting at: " + startDatePrint.get("hour") + ":" + startDatePrint.get("minutes") + " and ending at: " + endDatePrint.get("hour") + ":" + endDatePrint.get("minutes"));

		}
		
		System.out.println("--------------------");
		System.out.println("Get requested appointments of student: \n");
		
		ArrayList<Timeslot> requested = controller.getRequestedAppointments();
		
		for (Timeslot t : requested) {
			HashMap<String, Date> element = t.getAppointment();
			HashMap<String, Integer> startDatePrint = Timeslot.getDateInfo(element.get("startHour"));
			HashMap<String, Integer> endDatePrint = Timeslot.getDateInfo(element.get("endHour"));
			
			System.out.print("Id: " + t.getId() + " ");
			System.out.print("Day: " + startDatePrint.get("day") + "/" + startDatePrint.get("month"));
			System.out.println(" starting at: " + startDatePrint.get("hour") + ":" + startDatePrint.get("minutes") + " and ending at: " + endDatePrint.get("hour") + ":" + endDatePrint.get("minutes"));
		}
		
//		System.out.println("--------------------");
//		System.out.println("Accept an appointment");
//		
//		controller.acceptAppointmentRequest(controller.getRequestedTimeslotsById(7));

		//controller.rejectAppointmentRequested(controller.getRequestedTimeslotsById(5));
	
		for (Timeslot t : available) {
			HashMap<String, Date> element = t.getAppointment();
			HashMap<String, Integer> startDatePrint = Timeslot.getDateInfo(element.get("startHour"));
			HashMap<String, Integer> endDatePrint = Timeslot.getDateInfo(element.get("endHour"));
			
			System.out.print("Day: " + startDatePrint.get("day") + "/" + startDatePrint.get("month"));
			System.out.println(" starting at: " + startDatePrint.get("hour") + ":" + startDatePrint.get("minutes") + " and ending at: " + endDatePrint.get("hour") + ":" + endDatePrint.get("minutes"));

		}
		
		System.out.println("--------------------");
		System.out.println("Reserved Timeslots:");
		
		ArrayList<Timeslot> reserved = controller.getReservedTimeslots(controller.getAllProfessors().get(8));
		
		for (Timeslot t : reserved) {
			HashMap<String, Date> element = t.getAppointment();
			HashMap<String, Integer> startDatePrint = Timeslot.getDateInfo(element.get("startHour"));
			HashMap<String, Integer> endDatePrint = Timeslot.getDateInfo(element.get("endHour"));
			
			System.out.print("Day: " + startDatePrint.get("day") + "/" + startDatePrint.get("month"));
			System.out.println(" starting at: " + startDatePrint.get("hour") + ":" + startDatePrint.get("minutes") + " and ending at: " + endDatePrint.get("hour") + ":" + endDatePrint.get("minutes"));

		}
	}
}
