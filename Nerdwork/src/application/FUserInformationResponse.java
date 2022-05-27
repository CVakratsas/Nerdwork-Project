/*
 * Class used for temporary storage of user
 * profile information, when they are extracted
 * from the database. After that they are passed
 * to the User class.
 */

package application;

public class FUserInformationResponse {
	public boolean isSuccess;
	public String displayName;
	public String bio;
	public String email;
	
	public FUserInformationResponse(boolean success) {
		isSuccess = success;
	}
	
	public FUserInformationResponse(boolean success, String displayName, String bio, String email) {
		isSuccess = success;
		this.displayName = displayName;
		this.bio = bio;
		this.email = email;
	}
}