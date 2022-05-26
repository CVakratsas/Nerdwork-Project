import java.util.ArrayList;

public class FProfessorsResponse {
	public int id;
    public String name;
    public String phone;
    public String email;
    public String profilePhoto;
    public float rating;
    public FProfessorsResponse(int id, String name, String phone, String email, String profilePhoto, float rating) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.rating = rating;
    	this.email = email;
    	this.profilePhoto = profilePhoto;
    }
}
