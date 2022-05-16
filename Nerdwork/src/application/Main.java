package application;

public class Main  {
	
	public static void main(String[] args) {
		//Test to check if the password tester works properly
		User user_student1 = new User("user1", "user1Password!", "User 1", "user1@uom.edu.gr", "user1 Description");
		System.out.println(user_student1.getPassword()); //user1Password!
		System.out.println(user_student1.setPassword("1234567")); //false
		System.out.println(user_student1.setPassword("12345678")); //false
		System.out.println(user_student1.setPassword("12345678AB")); //false
		System.out.println(user_student1.setPassword("12345678AB@")); //false
		System.out.println(user_student1.setPassword("12345678Ab")); //false
		System.out.println(user_student1.setPassword("12345678ab@")); //false
		System.out.println(user_student1.setPassword("12345678Ab@")); //true
		System.out.println("New password: " + user_student1.getPassword()); //New password: 12345678Ab@
		
		System.out.println("----------------------");
		
		//Test to check if the Email verification is valid
		System.out.println(user_student1.getEmail()); //user1@uom.edu.gr
		System.out.println(user_student1.setEmail("user1@uon.edu.gr")); //false
		System.out.println(user_student1.setEmail("user1@uom.grr")); //false
		System.out.println(user_student1.setEmail("user1@")); //false
		System.out.println(user_student1.setEmail("@uom.gr")); //false
		System.out.println(user_student1.setEmail("user1@uom.edu.gr")); //true
		System.out.println(user_student1.setEmail("user1@uom.gr")); //true
		System.out.println("New email: " + user_student1.getEmail()); //New email: user1@uom.gr
		
		// Testing Professor for appointments:
		Professor prof = new Professor("prof1", "prof1Pass!", "Prof 1", "prof1@uom.gr", "prof1 Description");
		Student stud1 = new Student("stud1", "stud1Pass!", "Stud 1", "stud1@uom.edu.gr", "stud1 Des", "sddsa");
		Student stud2 = new Student("stud2", "stud2Pass@", "Stud 2", "stud2@uom.edu.gr", "stud2 Des", "das");
		
		System.out.println("\nCreate a new Timeslot");
		prof.addAvailableDate("25/01/2022");
		System.out.println(prof.getTimeslots().get(0).getDate()); // Created a new Timeslot
		
		System.out.println("\nRequests for appointment by the Students:");
		stud1.requestAppointment(prof, prof.getTimeslots().get(0));
		stud2.requestAppointment(prof, prof.getTimeslots().get(0));
		System.out.println(prof.getRequestedAppointments()); // Two requests created at the timeslot (both have different id)
		System.out.println(prof.getRequestedAppointments().get(0).getStudent()); 
		System.out.println(prof.getRequestedAppointments().get(1).getStudent());
		
		System.out.println("\nAccept a request and deny the other:");
		prof.acceptAppointment(prof.getRequestedAppointments().get(0));
		System.out.println(prof.getRequestedAppointments());
		System.out.println(prof.getTimeslots().get(0).getAvailability());
		
		System.out.println("\nCancel an appointment:");
		prof.cancelAppointment(prof.getTimeslots().get(0));
		System.out.println(prof.getTimeslots().get(0).getAvailability());
		
		
	}
	
}
