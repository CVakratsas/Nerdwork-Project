package application.gui.classes;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.Professor;
import application.functionality.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SettingsController {
	
	private User user;
	private GuiController controller;
	private FadeTransition fadeIn = new FadeTransition(Duration.millis(4000));
	private static final String dbURL = "https://nerdnet.geoxhonapps.com/cdn/profPhotos/";
	
	@FXML
	private ImageView profilePicture;
	@FXML
	private Label name;
	@FXML
	private Label email;
	@FXML
	private Label orientation;
	@FXML
	private TextField nameTF;
	@FXML
	private Label nameChangedResponse;
	@FXML
	private PasswordField oldPassPF;
	@FXML
	private PasswordField newPassPF;
	@FXML
	private PasswordField newPassRepeatPF;
	@FXML
	private Label passwordChangedResponse;
	@FXML
	private TextArea bioTA;
	@FXML
	private Label bioChangedResponse;
	@FXML
	private Button saveButton;

	
	public void initialize() throws IOException, ParseException {
		this.controller = GuiController.getInstance();
		this.user = controller.getUser();
		
		//Nodes that will fade when saveButton is pressed
		fadeIn.setNode(nameChangedResponse);
		passwordChangedResponse.opacityProperty().bind(nameChangedResponse.opacityProperty());
		bioChangedResponse.opacityProperty().bind(nameChangedResponse.opacityProperty());
		
	    fadeIn.setFromValue(1.0);
	    fadeIn.setToValue(0.0);
	    fadeIn.setCycleCount(1);
	    fadeIn.setAutoReverse(false);
	    
		
		setContentView();
	}
	
	
	private void setContentView() throws IOException, ParseException {
		
		//Loads User's information
		
		profilePicture.setImage(new Image("application/gui/icons/profile.png"));
		if(user instanceof Professor)
			profilePicture.setImage(new Image(dbURL + ((Professor) user).getProfilePhoto()));
			
		
		name.setText(user.getDisplayName());
		email.setText(user.getEmail());
		orientation.setText(User.Orientation[user.getOrientation()]);
		
		nameTF.setText(name.getText());
		bioTA.setText(user.getBio());
	}
	
	public void updateChanges() throws IOException, ParseException {
		changeName();
		changePassword();
		changeBio();
		
		fadeIn.playFromStart();
		
		initialize();
	}
	
	public void changeName() throws IOException {

		//No new name was provided
		if(nameTF.getText().equals(user.getDisplayName())) {
			nameChangedResponse.setText(""); //Clears Response Label
			return ;
		}
			
		boolean response = controller.setDisplayName(nameTF.getText());
		
		if(response) { //Name was Changed
			nameChangedResponse.setText("Name changed successfully");
			nameChangedResponse.setTextFill(Color.GREEN);
		}	
		else { //Name change failed. No changes were made
			nameChangedResponse.setText("No changes were made");
			nameChangedResponse.setTextFill(Color.RED);
		}	
	}
	
	public void changePassword() throws IOException {
		
		//No fields were provided
		if(oldPassPF.getText().equals("") && newPassPF.getText().equals("") && newPassRepeatPF.getText().equals("")) {
			passwordChangedResponse.setText(""); //Clears Response Label
			return ;
		}
			
		//Both new passwords are the same
		if(newPassPF.getText().equals(newPassRepeatPF.getText())){
			
			//Password change text response
			String response = controller.changePassword(oldPassPF.getText(), newPassPF.getText());
			passwordChangedResponse.setText(response);
			
			
			//Password change was successful
			if(response.equals("Ο κωδικός ενημερώθηκε επιτυχώς!")) {
				passwordChangedResponse.setTextFill(Color.GREEN);
				oldPassPF.clear();
				newPassPF.clear();
				newPassRepeatPF.clear();
			}
			else //Password change was unsuccessful
				passwordChangedResponse.setTextFill(Color.RED);
			
		}
		else { //New password miss-match
			passwordChangedResponse.setText("Οι καινούριοι κωδικοί είναι διαφορετικοί");
			passwordChangedResponse.setTextFill(Color.RED);
		}
	}
	
	public void changeBio() throws IOException {
		
		//No new Bio was provided
		if(bioTA.getText().equals(user.getBio())) {
			bioChangedResponse.setText(""); //Clears Response Label
			return ;
		}
		
		//Bio change response
		boolean response = controller.setBio(bioTA.getText());
		
		if(response) { //Bio change was succesfully
			bioChangedResponse.setText("Η περιγραφή ενημερώθηκε επιτυχώς!");
			bioChangedResponse.setTextFill(Color.GREEN);
		}
		else { //Bio change was unsuccesfully
			bioChangedResponse.setText("Η καινούργια περιγραφή είναι λανθασμένη");
			bioChangedResponse.setTextFill(Color.RED);
		}
		
	}
}
