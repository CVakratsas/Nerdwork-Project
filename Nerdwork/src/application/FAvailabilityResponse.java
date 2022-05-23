package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FAvailabilityResponse {
	
	public boolean isSuccess;
	public ArrayList<HashMap<String, Integer>> dates;
	
	public FAvailabilityResponse(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public FAvailabilityResponse(boolean isSuccess, ArrayList<HashMap<String, Integer>> dates) {
		this.isSuccess = isSuccess;
		this.dates = dates;
	}
	
}
