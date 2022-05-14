package application;

public class Main  {
	
	public static void main(String[] args) {
		//Test to check if the password tester works properly
		User user1 = new User("user1", "user1Password!", "User 1", "user1@uom.edu.gr", "user1 Description");
		System.out.println(user1.getPassword()); //user1Password!
		System.out.println(user1.setPassword("1234567")); //false
		System.out.println(user1.setPassword("12345678")); //false
		System.out.println(user1.setPassword("12345678AB")); //false
		System.out.println(user1.setPassword("12345678AB@")); //false
		System.out.println(user1.setPassword("12345678Ab")); //false
		System.out.println(user1.setPassword("12345678ab@")); //false
		System.out.println(user1.setPassword("12345678Ab@")); //true
		System.out.println("New password: " + user1.getPassword()); //New password: 12345678Ab@
		
		System.out.println("----------------------");
		
		System.out.println(user1.getEmail()); //user1@uom.edu.gr
		System.out.println(user1.setEmail("user1@uon.edu.gr")); //false
		System.out.println(user1.setEmail("user1@uom.grr")); //false
		System.out.println(user1.setEmail("user1@")); //false
		System.out.println(user1.setEmail("@uom.gr")); //false
		System.out.println(user1.setEmail("user1@uom.edu.gr")); //true
		System.out.println(user1.setEmail("user1@uom.gr")); //true
		System.out.println("New email: " + user1.getEmail()); //New email: user1@uom.gr
	}
	
}
