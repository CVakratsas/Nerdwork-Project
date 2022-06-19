package application.functionality;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.simple.parser.ParseException;

public class Main  {
	
	public static void main(String[] args) throws IOException, ParseException {
		//GUI Controller creation
		GuiController controller = GuiController.getInstance();
		
		//Login section
		// "probatos", "beeeH1234@" Student
		// "arnis", "123456789Ab!" Student2
		// "agelados", "1234abcdeF!" Student3
		// "example1", "123456789Ab!" Professor Manos Roumeliotis
		
		//System.out.println(controller.register("mpeees", "1234abcdeF!", "Mosxaris", "mp@uom.edu.gr", 1));
		
		System.out.println(controller.login("mpeees", "1234abcdeF!"));
		ArrayList<Timeslot> available = controller.getAvailableTimeslots(controller.getProfessorById(9));
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		ArrayList<Timeslot> available1 = controller.getAvailableTimeslots(controller.getProfessorById(9));
		System.out.println("-----------");
		for (Timeslot timeslot : available1) {
			Date startHour = new Date(timeslot.getStartHourTimestampMili());
			Date endHour = new Date(timeslot.getEndHourTimestampMili());
			
			System.out.println("Starting " + sdf.format(startHour) + " Ending " + sdf.format(endHour) + " status " + timeslot.getStatus());
		}
				
		System.out.println(controller.requestAppointment(controller.getProfessorById(9), available1.get(4)));
		
		ArrayList<Timeslot> available2 = controller.getAvailableTimeslots(controller.getProfessorById(9));
		System.out.println("Available-----------");
		for (Timeslot timeslot : available1) {
			Date startHour = new Date(timeslot.getStartHourTimestampMili());
			Date endHour = new Date(timeslot.getEndHourTimestampMili());
			
			System.out.println("Starting " + sdf.format(startHour) + " Ending " + sdf.format(endHour) + " status " + timeslot.getStatus());
		}
		
		ArrayList<Timeslot> requested = controller.getMyAppointments();
		System.out.println("MyAppointments-----------");
		for (Timeslot timeslot : requested) {
			Date startHour = new Date(timeslot.getStartHourTimestampMili());
			Date endHour = new Date(timeslot.getEndHourTimestampMili());
			
			System.out.println("Starting " + sdf.format(startHour) + " Ending " + sdf.format(endHour) + " status " + timeslot.getStatus() + " id " + timeslot.getId());

		}
		
		ArrayList<Timeslot> reserved = controller.getReservedTimeslots(controller.getProfessorById(9));
		System.out.println("Reserved-----------");
		for (Timeslot timeslot : reserved) {
			Date startHour = new Date(timeslot.getStartHourTimestampMili());
			Date endHour = new Date(timeslot.getEndHourTimestampMili());
			
			System.out.println("Starting " + sdf.format(startHour) + " Ending " + sdf.format(endHour) + " status " + timeslot.getStatus() + " id " + timeslot.getId() + " user id : " + timeslot.getStudentId());

		}
	}
}
