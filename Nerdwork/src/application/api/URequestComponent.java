package application.api;

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
	private String baseUrl;
    private boolean isAuthenticated;
    private String authCookie;
	
    public URequestComponent() {
		baseUrl = "https://nerdnet.geoxhonapps.com";
   	    isAuthenticated = false;
	}
	
    public static String getResponseContent(HttpURLConnection connection) throws IOException {
		InputStream is;
	
		if(connection.getResponseCode()==200) {
			is = connection.getInputStream();
		}
		
		else {
			is = connection.getErrorStream();
		}
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
        } 
        
        finally {
            reader.close();
            is.close();
        }
      
        return response.toString().trim();
    }
	
    public FRestResponse Post(String endpoint, JSONObject args) throws IOException {
    	URL url = new URL(baseUrl+endpoint);
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
		
    	http.setRequestMethod("POST");
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
    public FRestResponse Get(String endpoint) throws IOException {
    	URL url = new URL(baseUrl+endpoint);
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
    
    	http.setRequestMethod("GET");
    	http.setDoOutput(false);
    	
    	if(isAuthenticated) {
			http.setRequestProperty("Cookie", authCookie);
		}
    	
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
