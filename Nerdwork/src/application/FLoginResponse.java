/*
 * Class used to send to the main functionalities section of the 
 * program some data concerning the User objects.
 */

package application;

public class FLoginResponse{
	
	public boolean isSuccess; // If the http request was successful
	public String userId; // The id attribute
	public String displayName; // ??????
	public String username; // name attribute of the User class
	public int accountType; // Professor (1) or Student (0)
	
	public FLoginResponse(boolean success) {
		isSuccess = success;
	}
	
	public FLoginResponse(boolean success, String id, String Name, String username, int accountType) {
		isSuccess = success;
		userId = id;
		displayName = Name;
		this.username = username;
		this.accountType = accountType;
	}
	
}
