/*
 * Class containing information needed by the Course functionality class.
 * This class is used to transfer the data from the data base of the Course
 * object selected by the Student to the main program. 
 */

package application;

import java.util.ArrayList;

public class FSubjectsResponse {
	
	// Must contain all Course attributes.
    public String id;
    public String name;
    public ArrayList<String> associatedProfessors;
    public float rating;
    public int semester;
    
    public FSubjectsResponse(String id, String name, ArrayList<String>associatedProfessors, float rating, int semester) {
    	this.id = id;
    	this.name = name;
    	this.associatedProfessors = associatedProfessors;
    	this.rating = rating;
    	this.semester = semester;
    }
    
}
