package application.api;

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
