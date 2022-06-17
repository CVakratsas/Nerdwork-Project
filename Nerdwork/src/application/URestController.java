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
      Συνάρτηση για την σύνδεση του χρήστη με το API, επιστρέφει βασικες πληροφορίες σχετικά με τον χρήστη
      FLoginResponse για περισσότερες πληροφορίες.
      Απαιτείται Login για την κλήση των υπόλοιπων συναρτήσεων
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
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue(), ((Number)data.get("associatedProfessor")).intValue(), ((Number)data.get("orientation")).intValue());
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
    			 outResponse.add(new FProfessorsResponse(((Number)tempData.get("id")).intValue(), (String) tempData.get("name"), (String) tempData.get("phone"), (String) tempData.get("email"), (String) tempData.get("profilePhoto"), (String) tempData.get("office"), ((Number)tempData.get("rating")).floatValue(), (String) tempData.get("bio")));
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
       Επιστρέφει μια λίστα με όλα τα μαθήματα που είναι καταχωρημένα στην βάση δεδομένων.
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
    			 outResponse.add(new FSubjectsResponse(((String)tempData.get("id")), (String) tempData.get("name"), listdata, ((Number)tempData.get("rating")).floatValue(), ((Number)tempData.get("semester")).intValue(), ((Number)data.get("orientation")).intValue()));
    		 }
    		 return outResponse; 
    	 }
    	 return new ArrayList<FSubjectsResponse>();
     }
     
     /*
        Συνάρτηση για αξιολόγηση μαθήματος, μπορει να κληθεί μόνο μια φορά ανα μάθημα.
        Βαθμολογια πρεπει να ειναι απο 1-5
      */
     
     public boolean setSubjectRating(int rating, String subjectId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("rating", rating);
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/rating/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Συνάρτηση για ληψη βαθμολογίας ενός μαθήματος
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
     Συνάρτηση για ληψη βαθμολογίας που εχει θεσει ο χρηστης για ενα μαθημα
     -1 σε περιπτωση που δεν εχει βαθμολογησει το μαθημα.
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
     Συνάρτηση για ληψη εγγεγραμενων μαθηματων, επιστρεφει μια λιστα με τα id των μαθηματων.
   */
     
     public ArrayList<String> getEnrolledSubjects() throws IOException, ParseException{
    	 FRestResponse r = requestComponent.Get("/api/subjects/enrollments");
    	 if(r.statusCode==200) {
    		 JSONParser parser = new JSONParser();
    		 JSONObject data = (JSONObject) parser.parse(r.responseContent);
    		 data = (JSONObject) data.get("triggerResults");
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
     Συνάρτηση για εγγραφή σε μαθημα, μεγιστο 10 μαθήματα
   */
     
     public boolean enrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/enroll/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Συνάρτηση για απεγγραφή σε μαθημα, μεγιστο 10 μαθήματα
   */
     
     public boolean disenrollSubject(String subjectId) throws IOException{
    	 JSONObject obj = new JSONObject();
    	 obj.put("subjectId", subjectId);
    	 FRestResponse r = requestComponent.Post("/api/subjects/enrollments/disenroll/", obj);
    	 return r.statusCode==200;
     }
     
     /*
     Συνάρτηση για ληψη διαθεσιμων ημερομηνιων για ραντεβου ενος καθηγητη.
     Δειτε FAvailabilityResponse
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
      * Συνάρτηση για ληψη ηδη κλεισμενων ραντεβου ενος καθηγητη
      * Η μορφη ειναι timestamp.
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
     Συνάρτηση για ενημερωση των διαθεσιμων ημερομηνιων του καθηγητη.
     Μπορει να κληθει μονο αν accountType = 1, δειτε FLoginResponse.
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
      * Συνάρτηση για αποδοχή ραντεβού, μπορεί να κληθεί μόνο απο καθηγητή.
      * δεν μπορεί να κληθεί αν το ραντεβού είναι ακυρωμένο ή ηδη επιβεβαιωμένο
      */
     
     public boolean acceptAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/accept/", obj);
    	 return r.statusCode==200;
     }

     /*
      * Συνάρτηση για ακύρωση ραντεβού, μπορεί να κληθεί αν ο χρήστης ανήκει σε αυτό το ραντεβού.
      * δεν μπορεί να κληθεί αν το ραντεβού είναι ακυρωμένο.
      */
     
     public boolean cancelAppointment(int appointmentId) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("appointmentId", appointmentId);
    	 FRestResponse r = requestComponent.Post("/api/appointments/cancel/", obj);
    	 return r.statusCode==200;
     }
     
     /*
      * Συνάρτηση για κλείσιμο ραντεβού, μπορεί να κληθεί αν ο χρήστης είναι φοιτητής
      */
     
     public boolean bookAppointment(int professorId, int dateTimestamp) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("professorId", professorId);
    	 obj.put("timestamp", dateTimestamp);
    	 FRestResponse r = requestComponent.Post("/api/appointments/book/", obj);
    	 return r.statusCode==200;
     }
     
     /*
      * Συνάρτηση για ληψη βασικών πληροφοριών ενός χρήστη
      * Πολυ χρήσιμη για τα ραντεβου.
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
      * Συνάρτηση για να θεσουμε νεο display name
      */
     
     public boolean setDisplayName(String newDisplayName) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("displayName", newDisplayName);
    	 FRestResponse r = requestComponent.Put("/api/profile/displayName/", obj);
    	 return r.statusCode==200;
     }
     
     public boolean setBio(String bio) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("bio", bio);
    	 FRestResponse r = requestComponent.Put("/api/profile/bio/", obj);
    	 return r.statusCode==200;
     }
     
     public boolean setOrientation(int orientation) throws IOException {
    	 JSONObject obj = new JSONObject();
    	 obj.put("orientation", orientation);
    	 FRestResponse r = requestComponent.Put("/api/profile/orientation/", obj);
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
     Συνάρτηση για εγγραφη νεου χρηστη
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