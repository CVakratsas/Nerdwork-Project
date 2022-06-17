package application.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FAvailabilityResponse {
	public boolean isSuccess;
	/*
	  The following ArrayList of hashmaps has the following design
	  [index]->{"day": int, "startHour":int, "endHour":int}
	  
	  EXAMPLE:
	  [0]->{"day": 1, "startHour":15, "endHour":18}
	  [1]->{"day": 3, "startHour":20, "endHour":21}
	  [2]->{"day": 5, "startHour":12, "endHour":16}
	 */
	public ArrayList<HashMap<String, Integer>> dates;
	public FAvailabilityResponse(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public FAvailabilityResponse(boolean isSuccess, ArrayList<HashMap<String, Integer>> dates) {
		this.isSuccess = isSuccess;
		this.dates = dates;
	}
}
