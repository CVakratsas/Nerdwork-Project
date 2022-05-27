/*
 * Class for temporary storage of information extracted from
 * the database, concerning professors. The attributes are used to 
 * construct a new Professor object from the student's point
 * if view.
 */

package application;

import java.util.ArrayList;

public class FProfessorsResponse {
	public int id; // The Professor Id
    public String name; // Professor displayName
    public String phone;
    public String email;
    public String profilePhoto;
    public float rating;
   
    public FProfessorsResponse(int id, String name, String phone, String email, String profilePhoto, float rating) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.rating = rating;
    	this.email = email;
    	this.profilePhoto = profilePhoto;
    }
}
