package application.api;

import java.util.ArrayList;

public class FSubjectsResponse {
    public String id;
    public String name;
    public ArrayList<String> associatedProfessors;
    public float rating;
    public int semester;
    public int orientation;

    public FSubjectsResponse(String id, String name, ArrayList<String>associatedProfessors, float rating, int semester, int orientation) {
    	this.id = id;
    	this.name = name;
    	this.associatedProfessors = associatedProfessors;
    	this.rating = rating;
    	this.semester = semester;
    	this.orientation = orientation;

    }
}
