import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class URestController {
     private String baseUrl;
     private boolean isAuthenticated;
     private String authCookie;
     private String userId;
     private String username;
     private URestRequest requestComponent;
     public URestController() {
    	 baseUrl = "https://nerdnet.geoxhonapps.com";
    	 isAuthenticated = false;
    	 requestComponent = new URestRequest(this);
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
    		 isAuthenticated = true;
    		 userId = (String) data.get("id");
    		 System.out.println(data);
    		 authCookie = r.responseHeaders.get("set-cookie").get(0).split(";")[0];
    		 this.username = username;
    		 return new FLoginResponse(true, userId, (String)data.get("displayName"), username, ((Number)data.get("accountType")).intValue());
    	 }
    	 System.out.println(r.statusCode);
    	 return new FLoginResponse(false);
     }
     public String getBaseUrl() {
    	 return baseUrl;
     }
     public boolean isAuthenticated() {
    	 return isAuthenticated;
     }
     public String getAuthCookie() {
    	 return authCookie;
     }
}
