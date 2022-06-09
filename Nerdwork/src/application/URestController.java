/*
 * Class used to provide communication between the database
 * and the main program. It provides methods for extracting data 
 * needed by the main program from the database.
 * 
 * Note: must not be used anywhere else except the GuiController
 * class.
 */

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
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue(), ((Number)data.get("associatedProfessor")).intValue());
    	 }
    	 
    	 return new FLoginResponse(false);
     }
     
     /*
      * Method used to extract all of the information concerning professors, 
      * from the database. 
      * It returns an ArrayList consisting of FProfessorResponse objects 
      * (details for all professors) and has no parameters.
      */
     public ArrayList<FProfessorsResponse> getAllProfessors() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors");
    	
    	 if(r.statusCode == 200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults");
    	
    		 ArrayList<FProfessorsResponse> outResponse = new ArrayList<FProfessorsResponse>();
    		
    		 for(int i = 0; i < arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 outResponse.add(new FProfessorsResponse(((Number)tempData.get("id")).intValue(), (String) tempData.get("name"), (String) tempData.get("phone"), (String) tempData.get("email"), (String) tempData.get("profilePhoto"), (String) tempData.get("office"), ((Number)tempData.get("rating")).floatValue()));
    		 }
    		
    		 return outResponse;
    	 }
    	 
    	 return new ArrayList<FProfessorsResponse>();
     }
     
     /*
      * Method used to extract from the database a professor's rating,
      * by his id. 
      * It returns a float object (the professor's rating) and has
      * an integer type parameter (the professor's id whose rating will
      * be returned from the database).
      * 
      * Note concerning professor ids: as professor id we mean a unique number which is used to 
      * identify each professor. It is not to be confused with the userId,
      * which is used by both the Professor and Student objects and is used 
      * for security purposes.
      */
     public float getProfessorRating(int professorId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors/rating?professorId="+professorId);

    	 if(r.statusCode == 200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		
    		 return ((Number)(data.get("rating"))).floatValue();
    	 }
    
    	 return 0;
     }
     
     /*
      * Method used to extract from the database the stars that "this" student
      * has given to a certain professor, who is defined by his id.
      * It returns an integer type object (the stars "this" student has given
      * to the professor) and has an integer type parameter object (the professor's
      * id (see "Note concerning professor ids")).
      */
     public int getMyProfessorRating(int professorId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors/rating?professorId="+professorId);

    	 if(r.statusCode == 200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    	
    		 return ((Number)(data.get("myRating"))).intValue();
    	 }
    	 
    	 return -1;
     }

     /*
      * Method used to add stars (to rate) a professor, who is defined by
      * his id.
      * It returns true if the operation was successful (the rating operation was
      * successful and the server responded correctly) and false otherwise (the
      * rating operation failed (student already rated this professor) or the
      * server did not manage to respond correctly to the request) and gets
      * two parameters: an integer type object representing the stars given by the
      * student to the professor and an integer type object representing the professor's
      * id (whom the student is going to rate) (see "Note concerning professor ids").
      */
     public boolean setProfessorRating(int rating, int professorId) throws IOException {
    	 JSONObject obj = new JSONObject();
    
    	 obj.put("rating", rating);
    	 obj.put("professorId", professorId);
    	 FRestResponse r = requestComponent.Post("/api/professors/rating/", obj);
    
    	 return r.statusCode==200;
     }
     
     /*
      * Method used to extract all courses from the database.
      * It returns ArrayList consisting of FSubjectResponse objects,
      * containing details for each course contained in the database 
      * and gets no parameters.
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
      * Used to rate a course selected by the user with a number from 1 to 5 and save
      * any changes to the database (concerning subject rating).
      * It returns true if the operation was successful and the server responded correctly
      * and false if the operation was not successful (student already rated this subject)
      * or the server did not manage to respond correctly to the request.
      */
     public boolean setSubjectRating(int rating, String subjectId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 
    	 obj.put("rating", rating);
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/rating/", obj);
    	
    	 return r.statusCode==200;
     }
     
     /*
      * Method used to extract a subject's rating from the database.
      * It returns a float object representing the subject's total rating
      * and gets a String object as parameter (the selected subject's id).
      */
     public float getSubjectRating(String subjectId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/rating?subjectId="+subjectId); // Course URL
    	
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		
    		 return ((Number)(data.get("rating"))).floatValue(); // Returns current Course rating
    	}
    	 
    	 return -1;
     }
     
     /*
      * Method used to extract the rating (the stars) that "this" student has 
      * given to the selected subject, which is defined by its id.
      * It returns an integer object representing the stars given by "this" student
      * to the selected subject and gets a String object as parameter (the selected subject's
      * id).
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
      * Method that is used to extract the subjects that a student is enrolled to.
      * It returns an ArrayList consisting of String objects, each representing the enrolled
      * subject's name and gets no parameters.
      */
     public ArrayList<String> getEnrolledSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/enrollments"); // Redirection
//    	 System.out.println(r.responseContent);
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
      * Method used in order to enroll to a Course. Saves changes to 
      * the database.
      * It returns true if the enrollment operation was successful and
      * the server responded correctly and false if the enrollment operation
      * was unsuccessful (student already enrolled to this subject) or the server
      * did not manage to respond correctly to the request and gets a String object
      * (the subject's id to which the student chose to enroll to) as parameter.
      */ 
     public boolean enrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();

    	 obj.put("subjectId", subjectId);
    	 
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/enroll/", obj);
    	 System.out.println(r.responseContent);
    	 return r.statusCode == 200;
     }
     
     /*
      * 
      */
     public boolean disenrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/disenroll/", obj);
    	 
    	 return r.statusCode==200;
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
    		 data = (JSONObject)data.get("triggerResults");
    		 JSONArray arrayData = (JSONArray)data.get("availability"); // AVAILABLE Timelsots as a JSON object
   
    		 // AVAILABLE Timelsots as HashMap.
    		 ArrayList<HashMap<String, Integer>> dates = new ArrayList<HashMap<String, Integer>>();
    		
    		 // Parse the JSONArray into a HashMap:
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 HashMap<String, Integer> dateElement = new HashMap<String, Integer>();
    		
    			 dateElement.put("day", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("startHour", ((Number)tempData.get("startHour")).intValue());
    			 dateElement.put("endHour", ((Number)tempData.get("endHour")).intValue());
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
     
     public ArrayList<Integer> getBookedTimestamps(int professorId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/appointments/availability?professorId="+professorId); 
    	
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 JSONArray arrayData = (JSONArray)data.get("bookedTimestamps");
    		 
    		 ArrayList<Integer> booked = new ArrayList<Integer>();
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 booked.add(((Number)arrayData.get(i)).intValue());
    		 }
    		 
    		 return booked;
    	 }
    
    	 return new ArrayList<Integer>();
     }
     
     public ArrayList<FAppointmentsResponse> getMyAppointments() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/appointments");
    	 System.out.println(r.responseContent);
    	 
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults");
    		
    		 if(arrayData==null) {
    			 return new ArrayList<FAppointmentsResponse>();
    		 }
    		 ArrayList<FAppointmentsResponse> outResponse = new ArrayList<FAppointmentsResponse>();
    		 
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 outResponse.add(new FAppointmentsResponse(((Number)tempData.get("appointmentId")).intValue(), (String) tempData.get("studentId"), ((Number) tempData.get("professorId")).intValue(), ((Number) tempData.get("date")).intValue(), ((Number) tempData.get("status")).intValue(), (String) tempData.get("created_at")));
    		 }
    
    		 return outResponse;
    	 }
    
    	 return new ArrayList<FAppointmentsResponse>();
     }
     
     public boolean acceptAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/accept/", obj);
    	 
    	 return r.statusCode==200;
     }
     
     public boolean cancelAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/cancel/", obj);
    
    	 return r.statusCode==200;
     }
     
     public boolean bookAppointment(int professorId, int dateTimestamp) throws IOException {
    	 JSONObject obj = new JSONObject();
    
    	 obj.put("professorId", professorId);
    	 obj.put("timestamp", dateTimestamp);
    	 FRestResponse r = requestComponent.Post("/api/appointments/book/", obj);
    	 
    	 return r.statusCode==200;
     }
     
     public FUserInformationResponse getUserProfile(String userId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/profile?userId="+userId);
    	
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    	
    		 return new FUserInformationResponse(true, (String)data.get("displayName"), (String)data.get("bio"), (String)data.get("email"));
    	 }
    	
    	 return new FUserInformationResponse(false);
     }
     
     public boolean setDisplayName(String newDisplayName) throws IOException {
    	 JSONObject obj = new JSONObject();
    	
    	 obj.put("displayName", newDisplayName);
    	 FRestResponse r = requestComponent.Put("/api/profile/displayName/", obj);
    	 System.out.println(r.responseContent);
    	 
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
