package application.gui.classes;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SettingsController {
	
	private User user;
	private GuiController controller;
	
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
	private PasswordField oldPassPF;
	@FXML
	private PasswordField newPassPF;
	@FXML
	private PasswordField newPassRepeatPF;
	@FXML
	private ComboBox<String> orientationCB;
	@FXML
	private TextArea bioTA;
	@FXML
	private Button saveButton;

	public void initialize() throws IOException, ParseException {
		this.controller = GuiController.getInstance();
		this.user = controller.getUser();
		
		setContentView();
	}
	
	
	private void setContentView() throws IOException, ParseException {
		
		//Loads User's information
		name.setText(user.getDisplayName());
		email.setText(user.getEmail());
		
		nameTF.setText(name.getText());
		bioTA.setText("temp bio");
	}
	
	public void updateChanges() throws IOException, ParseException {
		changeName();
		changePassword();
		
		initialize();
	}
	
	public void changeName() {
		if(!nameTF.getText().equals(user.getDisplayName())) {
			//controller.setDisplayName(nameTF.getText());
		}
	}
	
	public void changePassword() throws IOException {
		System.out.println(controller.changePassword(oldPassPF.getText(), newPassPF.getText()));
	}
}
