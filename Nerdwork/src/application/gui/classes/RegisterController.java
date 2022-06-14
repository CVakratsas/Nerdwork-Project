package application.gui.classes;

import java.io.IOException;

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
	
	@FXML
	private TextField username, nickname, email;
	@FXML
	private PasswordField password, repeatPassword;
	@FXML
	private ImageView close;
	
	public void register(ActionEvent event) throws IOException, ParseException {
		
		boolean answer = GuiController.getInstance().register(username.getText(), password.getText(), nickname.getText(), email.getText());
		
		if(answer) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText("Account Created");
			alert.setContentText("Account was created sucessfully and is now ready for use");
			alert.initStyle(StageStyle.UTILITY);
			alert.showAndWait();			
			switchToLogin(event);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(null);
			alert.setHeaderText("Account was not created");
			alert.initStyle(StageStyle.UTILITY);
			if(username.getText().equals("") || password.getText().equals("") || repeatPassword.getText().equals("")
					|| nickname.getText().equals("") || email.getText().equals("")) {
				alert.setContentText("Some of the fields are empty");
			}
			else {
				alert.setContentText("Password does not meet the safety requirements or the email provided is not academic");
			}
			alert.showAndWait();
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
