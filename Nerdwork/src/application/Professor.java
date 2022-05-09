package application;

import java.util.ArrayList;

public class Professor extends User {
	
	/*Professor properties are here*/
	
	private Integer rating;
	private Integer ratingCounter;
	private Calendar myCalendar;
	
	/*Professor Constructor is here*/
	
	public Professor(String id, String password, String name, String email, String description) {
		super(id, password, name, email, description);
		rating = 0;
		ratingCounter = 0;
	}
	
	/*Professor methods (except getters and setters) are here*/
	
	public void addRate(Integer star) {
		rating += star;
		ratingCounter++;
	}

	public Calendar getMyCalendar() {
		return myCalendar;
	}
	
	/*Professor class Getters and Setters: */
	
	public Double getRating() {
		return (double) (rating/ratingCounter);
	}
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
}
