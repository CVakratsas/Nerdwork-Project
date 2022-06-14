package application.gui.classes;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SettingsController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
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
	private PasswordField newPassPF;
	@FXML
	private PasswordField newPassRepeatPF;
	@FXML
	private ComboBox<String> orientationCB;
	@FXML
	private TextArea bioTA;
	@FXML
	private Button saveButton;

	
	
	private void setContentView() throws IOException, ParseException {
		name.setText(controller.getDisplayNameStudent());
		email.setText(controller.getEmailStudent());
		
		nameTF.setText(name.getText());
		bioTA.setText("User bio goes here");
	}
	
	
	public void updateChanges() throws IOException, ParseException {
		if(!nameTF.getText().equals(controller.getDisplayNameStudent()))
			controller.setDisplayName(nameTF.getText());
			
		setContentView();
	}
	
	
	public void load() throws IOException, ParseException {
		this.controller = GuiController.getInstance();
		
		setContentView();
	}
	
	
	public void switchToSettings(Event event) throws IOException, ParseException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/settings.fxml"));
		root = loader.load();
		SettingsController settingsController = loader.getController();
		settingsController.load();
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
