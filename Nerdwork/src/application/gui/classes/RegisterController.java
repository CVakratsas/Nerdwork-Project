package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private ArrayList<String> orientationArray = new ArrayList<>(Arrays.asList("ΕΤΥ", "ΠΣ"));
	private int orientationInt;
	String error;
	
	@FXML
	private TextField username, nickname, email;
	@FXML
	private PasswordField password, repeatPassword;
	@FXML
	private ComboBox<String> orientation;
	@FXML
	private ImageView close;
	
	@FXML
	private void initialize() {
		if(orientation != null) {
			orientation.getItems().addAll(orientationArray);
		}
		else {
			orientationInt = 2;
		}
	}
	
	public void register(ActionEvent event) throws IOException, ParseException {
		
		boolean answer;
		if(orientationInt != 2) { // A student is trying to register
			String orientationString = orientation.getValue();
			if(orientationString == "ΕΤΥ") {
				orientationInt = 0;
				answer = GuiController.getInstance().register(username.getText(), password.getText(), nickname.getText(), email.getText(), orientationInt);
			}
			else if(orientationString == "ΠΣ") {
				orientationInt = 1;
				answer = GuiController.getInstance().register(username.getText(), password.getText(), nickname.getText(), email.getText(), orientationInt);
			}
			else {
				answer = false;
				GuiController.getInstance().alertFactory("Ανεπιτυχής Δημιουργία Λογαριασμού", "Κάποια ή όλα τα πεδία δεν είναι συμπληρωμένα"); // Orientation field is empty
				return;
			}
			
		}
		else { // A professor is trying to register
			answer = GuiController.getInstance().register(username.getText(), password.getText(), nickname.getText(), email.getText(), orientationInt);
		}
		
		if(answer) {
			GuiController.getInstance().alertFactory("Επιτυχής Δημιουργία Λογαριασμού", "Ο λογαριασμός δημιουργήθηκε και είναι έτοιμος προς χρήση");
			switchToLogin(event);
		}
		else {
			error = GuiController.getInstance().checkPassword(password.getText());
			if(username.getText().equals("") || password.getText().equals("") || repeatPassword.getText().equals("")
					|| nickname.getText().equals("") || email.getText().equals("")) { // There are empty fields
				GuiController.getInstance().alertFactory("Ανεπιτυχής Δημιουργία Λογαριασμού", "Κάποια ή όλα τα πεδία δεν είναι συμπληρωμένα");
			}
			else if(!password.getText().equals(repeatPassword.getText())) { // Passwords are not the same
				GuiController.getInstance().alertFactory("Ανεπιτυχής Δημιουργία Λογαριασμού", "Οι κωδικοί που καταχωρήθηκαν δεν είναι οι ίδιοι");
			}
			else if(!(GuiController.getInstance().checkPassword(password.getText()).equals("correct"))) {
				GuiController.getInstance().alertFactory("Ανεπιτυχής Δημιουργία Λογαριασμού", error);
			}
			else {
				GuiController.getInstance().alertFactory("Ανεπιτυχής Δημιουργία Λογαριασμού", "Το καταχωρημένο email δεν είναι ακαδημαϊκό ή ο λογιασμός ήδη υπάρχει");
			}
		}
	}
	
	public void switchToLogin(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.LoginPage));
		root = loader.load();
				
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void closeWindow() {
		
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
	        @Override
	        public void handle(MouseEvent event) {
	            Platform.exit();
	            event.consume();
	        }
	   });
	}
}
