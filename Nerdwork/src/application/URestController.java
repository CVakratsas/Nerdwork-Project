package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class URestController {
	
     private String userId; // User's id
     private String username; // User' name
     private URequestComponent requestComponent;
     
     public URestController() {
    	 requestComponent = new URequestComponent();
     }
     
     /*
      * Method used for logging in our web site with the given username and password.
      * The two parameters are given from the GUI
      */
     public FLoginResponse doLogin(String username, String password) throws IOException, ParseException {
    	 JSONObject obj = new JSONObject();
    	 
    	 obj.put("username", username);
	     obj.put("password", password);
	     
	     /* 
	      * Post the JSON containing the username and password to the login
	      * section of our web site
	      */
    	 FRestResponse r = requestComponent.Post("/api/auth/login/", obj);
    	 //System.out.println(r.responseContent);
    
    	 // If request succeeded:
    	 if(r.statusCode == 200) {
    		 JSONParser parser = new JSONParser();
    		 
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent); // Parse the response into a JSONObject
    		 // System.out.println(data);
    		 data = (JSONObject)data.get("triggerResults");
    		 userId = (String) data.get("id");
    		 this.username = username;
    		 
    		 // Returns FLoginResponse object with data required for the User objects
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue());
    	 }
    	 
    	 return new FLoginResponse(false);
     }
     
     /*
      * Method used to return all Courses. 
      */
     public ArrayList<FSubjectsResponse> getAllSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects"); // Change to another URL
    	 
    	 // If request successful:
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent); // Parse response contents into a JSON 
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults"); // Key in the JSON file
    		 // arrayData contains the Course attributes as JSON objects.
    		 
    		 // Courses will be stored here as FCourseResponse objects
    		 ArrayList<FSubjectsResponse> outResponse = new ArrayList<FSubjectsResponse>();
    		 
    		 // Parse the data of arrayData into String and store them in outResponse.
    		 for(int i = 0; i < arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 ArrayList<String> listdata = new ArrayList<String>();     
    			 JSONArray jArray = (JSONArray)tempData.get("associatedProfessors"); 
    			
    			 if (jArray != null)
    			    for (int j=0;j<jArray.size();j++) 
    			    	listdata.add((String) jArray.get(j));
    			  
    			 outResponse.add(new FSubjectsResponse(((String)tempData.get("id")), (String) tempData.get("name"), listdata, ((Number)tempData.get("rating")).floatValue(), ((Number)tempData.get("semester")).intValue()));
    		 }
    		 
    		 return outResponse; 
    	 }
    	 
    	 return new ArrayList<FSubjectsResponse>();
     }
     
     /*
      * Used to rate a Course selected by the user with a number from 1 to 5.
      */
     public boolean setSubjectRating(int rating, String subjectId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 
    	 obj.put("rating", rating);
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/rating/", obj);
    	 System.out.println(r.statusCode);
    	 return r.statusCode==200;
     }
     
     /*
      * Method used by the User to see the rating of the selected Course
      */
     public float getSubjectRating(String subjectId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/rating?subjectId="+subjectId); // Course URL
    	
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		
    		 return ((Number)(data.get("rating"))).floatValue(); // Returns current Course rating
    	}
    	 
    	 return 0;
     }
     
     /*
      * Method used by a User to see his rating a certain Course
      */
     public int getMySubjectRating(String subjectId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/rating?subjectId="+subjectId); // Course URL
    	
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		
    		 return ((Number)(data.get("myRating"))).intValue(); // Returns User's rating on the Course selected
    	 }
    	 return -1;
     }
     
     /*
      * Method that returns the Course which the Student has been enrolled to.
      */
     public ArrayList<String> getEnrolledSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/enrollments"); // Redirection
    	 System.out.println(r.responseContent);
    	 // If the redirection request is successful return enrolled Courses
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONObject enrollments = (JSONObject) data.get("triggerResults");
    		 JSONArray jArray = (JSONArray)enrollments.get("enrollments"); // Enrolled Courses as JSON objects

    		 ArrayList<String> listdata = new ArrayList<String>(); // Enrolled Courses as String type
    		 
    		 // Convert the JSON Courses into String
    		 if (jArray != null) 
 			    for (int j=0;j<jArray.size();j++) 
 			        listdata.add((String) jArray.get(j));
 			    
    		 return listdata; 
    	 }
    	 
    	 return new ArrayList<String>();
     }
     
     /*
      * Method used in order to enroll to a Course. Uses the Course's name
      * in order to do that.
      * The USer selects the Course.name to be enrolled to.
      */
     public boolean enrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();

    	 obj.put("subjectId", subjectId);
    	 
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/enroll/", obj);
    	 System.out.println(r.responseContent);
    	 return r.statusCode == 200;
     }
     
     /*
      * Method used to get the AVAILABLE Timeslots for appointment
      * with a Professor from the database.
      */
     public FAvailabilityResponse getAvailabilityDates(int professor) throws IOException, ParseException {
    	 FRestResponse r = requestComponent.Get("/api/appointments/availability?professor="+professor);
    	 
    	 // If redirection successful, return AVAILABLE Timeslots:
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults"); // AVAILABLE Timelsots as a JSON object
    		 
    		 // AVAILABLE Timelsots as HashMap.
    		 ArrayList<HashMap<String, Integer>> dates = new ArrayList<HashMap<String, Integer>>();
    		
    		 // Parse the JSONArray into a HashMap:
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 HashMap<String, Integer> dateElement = new HashMap<String, Integer>();
    		
    			 dateElement.put("day", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("startHour", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("endHour", ((Number)tempData.get("day")).intValue());
    			 dates.add(dateElement);
    		 }
    		 
    		 // Returns the AVAILABLE Timelsots.
    		 return new FAvailabilityResponse(true, dates);
    	 }
    	 
    	 return new FAvailabilityResponse(false);
     }
     
     /*
      * Method used by Professors to set a new available date
      * and be stored in the data base.
      */
     public boolean setAvailabilityDates(int day, int startHour, int endHour) throws IOException{
    	 JSONObject obj = new JSONObject();
    	
    	 obj.put("day", day);
    	 obj.put("startHour", startHour);
    	 obj.put("endHour", endHour);
    	
    	 FRestResponse r = requestComponent.Post("/api/appointments/availability/", obj);
    	
    	 return r.statusCode==200;
     }
     
     /*
      * Method used by Users when entering for the first time in 
      * our web site.
      */
     public boolean doRegister(String username, String password, String displayName, String email) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 
    	 obj.put("username", username);
	     obj.put("password", password);
	     obj.put("displayName", displayName);
	     obj.put("email", email);

	     FRestResponse r = requestComponent.Post("/api/auth/register/", obj);
    	 
    	 return r.statusCode == 200;
     }
     
}
