/*
 * Class used to send to the main functionalities section of the 
 * program some data concerning the User objects.
 */

package application;

public class FLoginResponse{
	public boolean isSuccess;
	public String userId;
	public String displayName;
	public String username;
	public int accountType;//��� �������� accountType=0, ��� ��������� accountType = 1
	public int associatedProfessorId; 
	
	public FLoginResponse(boolean success) {
		isSuccess = success;
	}
	
	public FLoginResponse(boolean success, String id, String Name, String username, int accountType, int associatedProfessorId) {
		isSuccess = success;
		userId = id;
		displayName = Name;
		this.username = username;
		this.accountType = accountType;
		this.associatedProfessorId = associatedProfessorId;
	}
}
