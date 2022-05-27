/*
 * Class that provides methods in order to contact
 * directly the database. It makes HTTP requests
 * to it.
 */

package application;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;

public class URequestComponent {
	
	private String baseUrl; // Url to connect to
    private boolean isAuthenticated;
    private String authCookie;
    
	public URequestComponent() {
		baseUrl = "https://nerdnet.geoxhonapps.com";
   	    isAuthenticated = false;
	}
	
	/*
	 * Method used to make requests to "this" URL by the User and return the 
	 * server's response.
	 * For example, if the User wants to see his Courses the Course details will 
	 * be returned.
	 */
	public static String getResponseContent(HttpURLConnection connection) throws IOException {
		InputStream is;
		
		// If http request was successful then read from open connection of "this" URL
		if(connection.getResponseCode()==200) {
			is = connection.getInputStream();
		}
		
		else {
			is = connection.getErrorStream();
		}
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(is)); // Reader of the input
        String line;
        StringBuffer response = new StringBuffer();
        
        try {
        	// Creating the response:
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
        } 
        
        finally {
            reader.close();
            is.close();
        }
        // System.out.println(response.toString().trim());
        // Returns the system's  response to the request made to "this" URL
        return response.toString().trim();
    }
	
	/*
	 * Method used for creating Post requests to the server by the user.
	 * It returns the status of the request (if it was successful, 
	 * the server's response to the request and some headers).
	 * Posts such as entering username and password, creating courses etc.
	 */
    public FRestResponse Post(String endpoint, JSONObject args) throws IOException {
    	URL url = new URL(baseUrl+endpoint); // expand the URL to fit to a new location
    	
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");
		
		// Not essential. To do with Cookies
		if(isAuthenticated) {
			http.setRequestProperty("Cookie", authCookie);
		}
		
		String data = args.toString();
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		
		stream.write(out);
		
		// For logging in the System.
		if(endpoint.contains("api/auth/login")) {
			if(http.getResponseCode() == 200) {
				isAuthenticated = true;
				authCookie = http.getHeaderFields().get("set-cookie").get(0).split(";")[0];
			}
		}
		
		return new FRestResponse(http.getResponseCode(), getResponseContent(http), http.getHeaderFields());
    }
    
    /*
     * Method used to change the current URL location to another one.
     * It returns the server's response to the relocation.
     */
    public FRestResponse Get(String endpoint) throws IOException {
    	URL url = new URL(baseUrl+endpoint);
    	
    	// Changing the URL and making it the active one.
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
    	http.setRequestMethod("GET");
    	http.setDoOutput(false);
    	
    	if(isAuthenticated) {
			http.setRequestProperty("Cookie", authCookie);
		}
    	
    	// Return the servers response and new contents
    	return new FRestResponse(http.getResponseCode(), getResponseContent(http), http.getHeaderFields());
    }
    
    public FRestResponse Put(String endpoint, JSONObject args) throws IOException {
    	URL url = new URL(baseUrl+endpoint);
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
		
    	http.setRequestMethod("PUT");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");
		
		if(isAuthenticated) {
			http.setRequestProperty("Cookie", authCookie);
		}
		
		String data = args.toString();
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);
		
		if(endpoint.contains("api/auth/login")) {
			if(http.getResponseCode()==200) {
				isAuthenticated = true;
				authCookie = http.getHeaderFields().get("set-cookie").get(0).split(";")[0];
			}
		}
		
		return new FRestResponse(http.getResponseCode(), getResponseContent(http), http.getHeaderFields());
    }
}
