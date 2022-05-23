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
	
     private String userId;
     private String username;
     private URequestComponent requestComponent;
     
     public URestController() {
    	 requestComponent = new URequestComponent();
     }
     
     public FLoginResponse doLogin(String username, String password) throws IOException, ParseException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("username", username);
	     obj.put("password", password);
    	 FRestResponse r = requestComponent.Post("/api/auth/login/", obj);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 userId = (String) data.get("id");
    		 this.username = username;
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue());
    	 }
    	 return new FLoginResponse(false);
     }
     
     public ArrayList<FSubjectsResponse> getAllSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects");
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults");
    		 ArrayList<FSubjectsResponse> outResponse = new ArrayList<FSubjectsResponse>();
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 ArrayList<String> listdata = new ArrayList<String>();     
    			 JSONArray jArray = (JSONArray)tempData.get("associatedProfessors"); 
    			 if (jArray != null) { 
    			    for (int j=0;j<jArray.size();j++){ 
    			     listdata.add((String) jArray.get(j));
    			    } 
    			 }
    			 outResponse.add(new FSubjectsResponse(((String)tempData.get("id")), (String) tempData.get("name"), listdata, ((Number)tempData.get("rating")).floatValue(), ((Number)tempData.get("semester")).intValue()));
    		 }
    		 return outResponse; 
    	 }
    	 return new ArrayList<FSubjectsResponse>();
     }
     
     public boolean setSubjectRating(int rating, String subjectId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("rating", rating);
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/rating/", obj);
    	 return r.statusCode==200;
     }
     
     public float getSubjectRating(String subjectId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/rating?subjectId="+subjectId);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 return ((Number)(data.get("rating"))).floatValue();
    	 }
    	 return 0;
     }
     
     public int getMySubjectRating(String subjectId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/rating?subjectId="+subjectId);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 return ((Number)(data.get("myRating"))).intValue();
    	 }
    	 return -1;
     }
     
     public ArrayList<String> getEnrolledSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/enrollments");
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray jArray = (JSONArray)data.get("triggerResults");
    		 ArrayList<String> listdata = new ArrayList<String>();
    		 if (jArray != null) { 
 			    for (int j=0;j<jArray.size();j++){ 
 			        listdata.add((String) jArray.get(j));
 			    } 
 			 }
    		 return listdata; 
    	 }
    	 return new ArrayList<String>();
     }
     
     public boolean enrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/enroll/", obj);
    	 return r.statusCode==200;
     }
     
     public FAvailabilityResponse getAvailabilityDates(int professor) throws IOException, ParseException {
    	 FRestResponse r = requestComponent.Get("/api/appointments/availability?professor="+professor);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults");
    		 ArrayList<HashMap<String, Integer>> dates = new ArrayList<HashMap<String, Integer>>();
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 HashMap<String, Integer> dateElement = new HashMap<String, Integer>();
    			 dateElement.put("day", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("startHour", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("endHour", ((Number)tempData.get("day")).intValue());
    			 dates.add(dateElement);
    		 }
    		 return new FAvailabilityResponse(true, dates);
    	 }
    	 return new FAvailabilityResponse(false);
     }
     
     public boolean setAvailabilityDates(int day, int startHour, int endHour) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("day", day);
    	 obj.put("startHour", startHour);
    	 obj.put("endHour", endHour);
    	 FRestResponse r = requestComponent.Post("/api/appointments/availability/", obj);
    	 return r.statusCode==200;
     }
     
     public boolean doRegister(String username, String password, String displayName, String email) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("username", username);
	     obj.put("password", password);
	     obj.put("displayName", displayName);
	     obj.put("email", email);
    	 FRestResponse r = requestComponent.Post("/api/auth/register/", obj);
    	 return r.statusCode==200;
     }
     
}
