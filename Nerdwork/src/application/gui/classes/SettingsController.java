package application.gui.classes;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SettingsController {
	
	private User user;
	private GuiController controller;
	private FadeTransition fadeIn = new FadeTransition(Duration.millis(3000));
	
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
	private ComboBox<String> orientationCB;
	@FXML
	private TextArea bioTA;
	@FXML
	private Button saveButton;

	public void initialize() throws IOException, ParseException {
		this.controller = GuiController.getInstance();
		this.user = controller.getUser();
		
		//Nodes that will fade when saveButton is pressed
		fadeIn.setNode(nameChangedResponse);
		passwordChangedResponse.opacityProperty().bind(nameChangedResponse.opacityProperty());
		
	    fadeIn.setFromValue(1.0);
	    fadeIn.setToValue(0.0);
	    fadeIn.setCycleCount(1);
	    fadeIn.setAutoReverse(false);
		
		
		setContentView();
	}
	
	
	private void setContentView() throws IOException, ParseException {
		
		//Loads User's information
		name.setText(user.getDisplayName());
		email.setText(user.getEmail());
		
		nameTF.setText(name.getText());
		bioTA.setText(user.getBio());
		
	}
	
	public void updateChanges() throws IOException, ParseException {
		changeName();
		changePassword();
		
		fadeIn.playFromStart();
		
		initialize();
	}
	
	public void changeName() throws IOException {
		
		boolean result = false;
		if(!nameTF.getText().equals(user.getDisplayName())) {
			result = controller.setDisplayName(nameTF.getText());
		}
		else //Name wasn't changed. User didn't provide a new name
			nameChangedResponse.setText(""); //Clears Response Label

		
		if(result) { //Name was Changed
			nameChangedResponse.setText("Name changed successfully");
			nameChangedResponse.setTextFill(Color.GREEN);
		}
			
		else { //Name change failed. No changes were made
			nameChangedResponse.setText("No changes were made");
			nameChangedResponse.setTextFill(Color.RED);
		}
	}
	
	public void changePassword() throws IOException {
		
		//If no fields were provided
		if(oldPassPF.getText().equals("") && newPassPF.getText().equals("") && newPassRepeatPF.getText().equals("")) {
			passwordChangedResponse.setText(""); //Clears Response Label
			return ;
		}
			
		//If both new passwords are the same
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
}
