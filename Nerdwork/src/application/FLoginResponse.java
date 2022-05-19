
public class FLoginResponse{
	public boolean isSuccess;
	public String userId;
	public String displayName;
	public String username;
	public int accountType;//Για φοιτητες accountType=0, για καθηγητες accountType = 1
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
