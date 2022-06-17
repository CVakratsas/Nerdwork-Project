package application.api;

public class FProfessorsResponse {
	public int id;
    public String name;
    public String phone;
    public String email;
    public String profilePhoto;
    public String office;
    public float rating;
    public FProfessorsResponse(int id, String name, String phone, String email, String profilePhoto, String office, float rating) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.rating = rating;
    	this.email = email;
    	this.office = office;
    	this.profilePhoto = profilePhoto;
    }
}