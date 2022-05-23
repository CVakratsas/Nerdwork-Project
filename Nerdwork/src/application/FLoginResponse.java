package application;

public class FLoginResponse{
	
	public boolean isSuccess;
	public String userId;
	public String displayName;
	public String username;
	public int accountType;
	
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
