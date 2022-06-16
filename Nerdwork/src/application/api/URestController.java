package application.api;
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
     /*
      Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ο„Ξ·Ξ½ ΟƒΟ�Ξ½Ξ΄ΞµΟƒΞ· Ο„ΞΏΟ… Ο‡Ο�Ξ®ΟƒΟ„Ξ· ΞΌΞµ Ο„ΞΏ API, ΞµΟ€ΞΉΟƒΟ„Ο�Ξ­Ο†ΞµΞΉ Ξ²Ξ±ΟƒΞΉΞΊΞµΟ‚ Ο€Ξ»Ξ·Ο�ΞΏΟ†ΞΏΟ�Ξ―ΞµΟ‚ ΟƒΟ‡ΞµΟ„ΞΉΞΊΞ¬ ΞΌΞµ Ο„ΞΏΞ½ Ο‡Ο�Ξ®ΟƒΟ„Ξ·
      FLoginResponse Ξ³ΞΉΞ± Ο€ΞµΟ�ΞΉΟƒΟƒΟ�Ο„ΞµΟ�ΞµΟ‚ Ο€Ξ»Ξ·Ο�ΞΏΟ†ΞΏΟ�Ξ―ΞµΟ‚.
      Ξ‘Ο€Ξ±ΞΉΟ„ΞµΞ―Ο„Ξ±ΞΉ Login Ξ³ΞΉΞ± Ο„Ξ·Ξ½ ΞΊΞ»Ξ®ΟƒΞ· Ο„Ο‰Ξ½ Ο…Ο€Ο�Ξ»ΞΏΞΉΟ€Ο‰Ξ½ ΟƒΟ…Ξ½Ξ±Ο�Ο„Ξ®ΟƒΞµΟ‰Ξ½
      */
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
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue(), ((Number)data.get("associatedProfessor")).intValue());
    	 }
    	 return new FLoginResponse(false);
     }
     
     public ArrayList<FProfessorsResponse> getAllProfessors() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors");
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray arrayData = (JSONArray)data.get("triggerResults");
    		 ArrayList<FProfessorsResponse> outResponse = new ArrayList<FProfessorsResponse>();
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 outResponse.add(new FProfessorsResponse(((Number)tempData.get("id")).intValue(), (String) tempData.get("name"), (String) tempData.get("phone"), (String) tempData.get("email"), (String) tempData.get("profilePhoto"), (String) tempData.get("office"), ((Number)tempData.get("rating")).floatValue()));
    		 }
    		 return outResponse;
    	 }
    	 return new ArrayList<FProfessorsResponse>();
     }
     
     public float getProfessorRating(int professorId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors/rating?professorId="+professorId);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 return ((Number)(data.get("rating"))).floatValue();
    	 }
    	 return 0;
     }
     
     public int getMyProfessorRating(int professorId) throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/professors/rating?professorId="+professorId);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 return ((Number)(data.get("myRating"))).intValue();
    	 }
    	 return -1;
     }
     
     public boolean setProfessorRating(int rating, int professorId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("rating", rating);
    	 obj.put("professorId", professorId);
    	 FRestResponse r = requestComponent.Post("/api/professors/rating/", obj);
    	 return r.statusCode==200;
     }
     
     /*
       Ξ•Ο€ΞΉΟƒΟ„Ο�Ξ­Ο†ΞµΞΉ ΞΌΞΉΞ± Ξ»Ξ―ΟƒΟ„Ξ± ΞΌΞµ Ο�Ξ»Ξ± Ο„Ξ± ΞΌΞ±ΞΈΞ®ΞΌΞ±Ο„Ξ± Ο€ΞΏΟ… ΞµΞ―Ξ½Ξ±ΞΉ ΞΊΞ±Ο„Ξ±Ο‡Ο‰Ο�Ξ·ΞΌΞ­Ξ½Ξ± ΟƒΟ„Ξ·Ξ½ Ξ²Ξ¬ΟƒΞ· Ξ΄ΞµΞ΄ΞΏΞΌΞ­Ξ½Ο‰Ξ½.
      */
     
     public ArrayList<FSubjectsResponse> getAllSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects");
    	 System.out.println(r.responseContent);
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
     
     /*
        Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ±ΞΎΞΉΞΏΞ»Ο�Ξ³Ξ·ΟƒΞ· ΞΌΞ±ΞΈΞ®ΞΌΞ±Ο„ΞΏΟ‚, ΞΌΟ€ΞΏΟ�ΞµΞΉ Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― ΞΌΟ�Ξ½ΞΏ ΞΌΞΉΞ± Ο†ΞΏΟ�Ξ¬ Ξ±Ξ½Ξ± ΞΌΞ¬ΞΈΞ·ΞΌΞ±.
        Ξ’Ξ±ΞΈΞΌΞΏΞ»ΞΏΞ³ΞΉΞ± Ο€Ο�ΞµΟ€ΞµΞΉ Ξ½Ξ± ΞµΞΉΞ½Ξ±ΞΉ Ξ±Ο€ΞΏ 1-5
      */
     
     public boolean setSubjectRating(int rating, String subjectId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("rating", rating);
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/rating/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· Ξ²Ξ±ΞΈΞΌΞΏΞ»ΞΏΞ³Ξ―Ξ±Ο‚ ΞµΞ½Ο�Ο‚ ΞΌΞ±ΞΈΞ®ΞΌΞ±Ο„ΞΏΟ‚
   */
     
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
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· Ξ²Ξ±ΞΈΞΌΞΏΞ»ΞΏΞ³Ξ―Ξ±Ο‚ Ο€ΞΏΟ… ΞµΟ‡ΞµΞΉ ΞΈΞµΟƒΞµΞΉ ΞΏ Ο‡Ο�Ξ·ΟƒΟ„Ξ·Ο‚ Ξ³ΞΉΞ± ΞµΞ½Ξ± ΞΌΞ±ΞΈΞ·ΞΌΞ±
     -1 ΟƒΞµ Ο€ΞµΟ�ΞΉΟ€Ο„Ο‰ΟƒΞ· Ο€ΞΏΟ… Ξ΄ΞµΞ½ ΞµΟ‡ΞµΞΉ Ξ²Ξ±ΞΈΞΌΞΏΞ»ΞΏΞ³Ξ·ΟƒΞµΞΉ Ο„ΞΏ ΞΌΞ±ΞΈΞ·ΞΌΞ±.
   */
     
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
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· ΞµΞ³Ξ³ΞµΞ³Ο�Ξ±ΞΌΞµΞ½Ο‰Ξ½ ΞΌΞ±ΞΈΞ·ΞΌΞ±Ο„Ο‰Ξ½, ΞµΟ€ΞΉΟƒΟ„Ο�ΞµΟ†ΞµΞΉ ΞΌΞΉΞ± Ξ»ΞΉΟƒΟ„Ξ± ΞΌΞµ Ο„Ξ± id Ο„Ο‰Ξ½ ΞΌΞ±ΞΈΞ·ΞΌΞ±Ο„Ο‰Ξ½.
   */
     
     public ArrayList<String> getEnrolledSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/enrollments");
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 JSONArray jArray = (JSONArray)data.get("enrollments");
    		 if(jArray==null) {
    			 return new ArrayList<String>();
    		 }
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
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± ΞµΞ³Ξ³Ο�Ξ±Ο†Ξ® ΟƒΞµ ΞΌΞ±ΞΈΞ·ΞΌΞ±, ΞΌΞµΞ³ΞΉΟƒΟ„ΞΏ 10 ΞΌΞ±ΞΈΞ®ΞΌΞ±Ο„Ξ±
   */
     
     public boolean enrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/enroll/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ±Ο€ΞµΞ³Ξ³Ο�Ξ±Ο†Ξ® ΟƒΞµ ΞΌΞ±ΞΈΞ·ΞΌΞ±, ΞΌΞµΞ³ΞΉΟƒΟ„ΞΏ 10 ΞΌΞ±ΞΈΞ®ΞΌΞ±Ο„Ξ±
   */
     
     public boolean disenrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/disenroll/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· Ξ΄ΞΉΞ±ΞΈΞµΟƒΞΉΞΌΟ‰Ξ½ Ξ·ΞΌΞµΟ�ΞΏΞΌΞ·Ξ½ΞΉΟ‰Ξ½ Ξ³ΞΉΞ± Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ… ΞµΞ½ΞΏΟ‚ ΞΊΞ±ΞΈΞ·Ξ³Ξ·Ο„Ξ·.
     Ξ”ΞµΞΉΟ„Ξµ FAvailabilityResponse
   */
     
     public FAvailabilityResponse getAvailabilityDates(int professorId) throws IOException, ParseException {
    	 FRestResponse r = requestComponent.Get("/api/appointments/availability?professorId="+professorId);
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject)data.get("triggerResults");
    		 JSONArray arrayData = (JSONArray)data.get("availability");
    		 ArrayList<HashMap<String, Integer>> dates = new ArrayList<HashMap<String, Integer>>();
    		 for(int i = 0; i<arrayData.size(); i++) {
    			 JSONObject tempData = (JSONObject)arrayData.get(i);
    			 HashMap<String, Integer> dateElement = new HashMap<String, Integer>();
    			 dateElement.put("day", ((Number)tempData.get("day")).intValue());
    			 dateElement.put("startHour", ((Number)tempData.get("startHour")).intValue());
    			 dateElement.put("endHour", ((Number)tempData.get("endHour")).intValue());
    			 dates.add(dateElement);
    		 }
    		 return new FAvailabilityResponse(true, dates);
    	 }
    	 return new FAvailabilityResponse(false);
     }
     
     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· Ξ·Ξ΄Ξ· ΞΊΞ»ΞµΞΉΟƒΞΌΞµΞ½Ο‰Ξ½ Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ… ΞµΞ½ΞΏΟ‚ ΞΊΞ±ΞΈΞ·Ξ³Ξ·Ο„Ξ·
      * Ξ— ΞΌΞΏΟ�Ο†Ξ· ΞµΞΉΞ½Ξ±ΞΉ timestamp.
      */
     
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
     
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± ΞµΞ½Ξ·ΞΌΞµΟ�Ο‰ΟƒΞ· Ο„Ο‰Ξ½ Ξ΄ΞΉΞ±ΞΈΞµΟƒΞΉΞΌΟ‰Ξ½ Ξ·ΞΌΞµΟ�ΞΏΞΌΞ·Ξ½ΞΉΟ‰Ξ½ Ο„ΞΏΟ… ΞΊΞ±ΞΈΞ·Ξ³Ξ·Ο„Ξ·.
     Ξ�Ο€ΞΏΟ�ΞµΞΉ Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞΉ ΞΌΞΏΞ½ΞΏ Ξ±Ξ½ accountType = 1, Ξ΄ΞµΞΉΟ„Ξµ FLoginResponse.
   */
     
     public boolean setAvailabilityDates(int day, int startHour, int endHour) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("day", day);
    	 obj.put("startHour", startHour);
    	 obj.put("endHour", endHour);
    	 FRestResponse r = requestComponent.Post("/api/appointments/availability/", obj);
    	 return r.statusCode==200;
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
     
     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ±Ο€ΞΏΞ΄ΞΏΟ‡Ξ® Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ�, ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― ΞΌΟ�Ξ½ΞΏ Ξ±Ο€ΞΏ ΞΊΞ±ΞΈΞ·Ξ³Ξ·Ο„Ξ®.
      * Ξ΄ΞµΞ½ ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― Ξ±Ξ½ Ο„ΞΏ Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ� ΞµΞ―Ξ½Ξ±ΞΉ Ξ±ΞΊΟ…Ο�Ο‰ΞΌΞ­Ξ½ΞΏ Ξ® Ξ·Ξ΄Ξ· ΞµΟ€ΞΉΞ²ΞµΞ²Ξ±ΞΉΟ‰ΞΌΞ­Ξ½ΞΏ
      */
     
     public boolean acceptAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/accept/", obj);
    	 return r.statusCode==200;
     }

     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ±ΞΊΟ�Ο�Ο‰ΟƒΞ· Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ�, ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― Ξ±Ξ½ ΞΏ Ο‡Ο�Ξ®ΟƒΟ„Ξ·Ο‚ Ξ±Ξ½Ξ®ΞΊΞµΞΉ ΟƒΞµ Ξ±Ο…Ο„Ο� Ο„ΞΏ Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ�.
      * Ξ΄ΞµΞ½ ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― Ξ±Ξ½ Ο„ΞΏ Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ� ΞµΞ―Ξ½Ξ±ΞΉ Ξ±ΞΊΟ…Ο�Ο‰ΞΌΞ­Ξ½ΞΏ.
      */
     
     public boolean cancelAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/cancel/", obj);
    	 return r.statusCode==200;
     }
     
     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± ΞΊΞ»ΞµΞ―ΟƒΞΉΞΌΞΏ Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ�, ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ± ΞΊΞ»Ξ·ΞΈΞµΞ― Ξ±Ξ½ ΞΏ Ο‡Ο�Ξ®ΟƒΟ„Ξ·Ο‚ ΞµΞ―Ξ½Ξ±ΞΉ Ο†ΞΏΞΉΟ„Ξ·Ο„Ξ®Ο‚
      */
     
     public boolean bookAppointment(int professorId, int dateTimestamp) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("professorId", professorId);
    	 obj.put("timestamp", dateTimestamp);
    	 FRestResponse r = requestComponent.Post("/api/appointments/book/", obj);
    	 return r.statusCode==200;
     }
     
     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ»Ξ·Ο�Ξ· Ξ²Ξ±ΟƒΞΉΞΊΟ�Ξ½ Ο€Ξ»Ξ·Ο�ΞΏΟ†ΞΏΟ�ΞΉΟ�Ξ½ ΞµΞ½Ο�Ο‚ Ο‡Ο�Ξ®ΟƒΟ„Ξ·
      * Ξ ΞΏΞ»Ο… Ο‡Ο�Ξ®ΟƒΞΉΞΌΞ· Ξ³ΞΉΞ± Ο„Ξ± Ο�Ξ±Ξ½Ο„ΞµΞ²ΞΏΟ….
      */
     
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
     
     /*
      * Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± Ξ½Ξ± ΞΈΞµΟƒΞΏΟ…ΞΌΞµ Ξ½ΞµΞΏ display name
      */
     
     public boolean setDisplayName(String newDisplayName) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("displayName", newDisplayName);
    	 FRestResponse r = requestComponent.Put("/api/profile/displayName/", obj);
    	 System.out.println(r.responseContent);
    	 return r.statusCode==200;
     }
     
     public boolean setPassword(String oldPassword, String newPassword) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("oldPassword", oldPassword);
    	 obj.put("newPassword", newPassword);
    	 FRestResponse r = requestComponent.Post("/api/auth/password/", obj);
    	 return r.statusCode==200;
     }
	
     /*
     Ξ£Ο…Ξ½Ξ¬Ο�Ο„Ξ·ΟƒΞ· Ξ³ΞΉΞ± ΞµΞ³Ξ³Ο�Ξ±Ο†Ξ· Ξ½ΞµΞΏΟ… Ο‡Ο�Ξ·ΟƒΟ„Ξ·
   */
     
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
