
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
	public static void main(String[] args){
		
		URestController  restController = new URestController();
		try {
			restController.doLogin("example", "12345678");
			restController.getAllSubjects();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
