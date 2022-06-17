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
			System.out.println("Hello prof");
			orientationInt = 2;
		}
	}
	
	public void register(ActionEvent event) throws IOException, ParseException {
		
//		if(orientationInt != 2) { // A student is trying to register
//			String orientationString = orientation.getValue();
//			if(orientationString == "ΕΤΥ") {
//				orientationInt = 0;
//			}
//			else if(orientationString == "ΠΣ") {
//				orientationInt = 1;
//			}
//			else {
//				alertError1();
//			}
//		}
//		else { // A professor is trying to register
			boolean answer = GuiController.getInstance().register(username.getText(), password.getText(), nickname.getText(), email.getText());
			
			if(answer) {
				alertSucess();
				switchToLogin(event);
			}
			else {
				error = GuiController.getInstance().checkPassword(password.getText());
				if(username.getText().equals("") || password.getText().equals("") || repeatPassword.getText().equals("")
						|| nickname.getText().equals("") || email.getText().equals("")) { // There are empty fields
					alertError1();
				}
				else if(!password.getText().equals(repeatPassword.getText())) { // Passwords are not the same
					alertError2();
				}
				else if(!(GuiController.getInstance().checkPassword(password.getText()).equals("correct"))) {
					alertError3(error);
				}
				else {
					alertError4();
				}
			}
//		}
	}
	
	public void alertError1() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(null);
		alert.setHeaderText("Αποτυχία Δημιουργίας Λογαριασμού");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText("Κάποια ή όλα τα πεδία είναι άδεια");
		alert.showAndWait();
	}
	
	public void alertError2() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(null);
		alert.setHeaderText("Αποτυχία Δημιουργίας Λογαριασμού");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText("Οι κωδικοί που καταχωρήθηκαν δεν είναι οι ίδιοι");
		alert.showAndWait();
	}
	
	public void alertError3(String error) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(null);
		alert.setHeaderText("Αποτυχία Δημιουργίας Λογαριασμού");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText(error);
		alert.showAndWait();
	}
	
	public void alertError4() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(null);
		alert.setHeaderText("Αποτυχία Δημιουργίας Λογαριασμού");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText("Το email που δώθηκε δεν είναι ακαδημαϊκό ή ο λογαριασμός ήδη υπάρχει");
		alert.showAndWait();
	}
	
	public void alertSucess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText("Επιτυχής Δημιουργία Λογαριασμού");
		alert.setContentText("Ο λογαριασμός δημιουργήθηκε και είναι έτοιμος προς χρήση");
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();			
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
