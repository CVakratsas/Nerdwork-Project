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
public class URestRequest {
	private URestController restController;
	public URestRequest(URestController controller) {
		restController = controller;
	}
	public static String getResponseContent(HttpURLConnection connection) throws IOException {
		InputStream is;
		if(connection.getResponseCode()==200) {
			is = connection.getInputStream();
		}else {
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
        } finally {
            reader.close();
            is.close();
        }
        return response.toString().trim();
    }
	
    public FRestResponse Post(String endpoint, JSONObject args) throws IOException {
    	URL url = new URL(restController.getBaseUrl()+endpoint);
    	HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");
		if(restController.isAuthenticated()) {
			http.setRequestProperty("Cookie", restController.getAuthCookie());
		}
		String data = args.toString();
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);
		return new FRestResponse(http.getResponseCode(), getResponseContent(http), http.getHeaderFields());
    }
}
