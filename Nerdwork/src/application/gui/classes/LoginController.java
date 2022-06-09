package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	ImageView close;
	
	public void switchToRegister(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Register.fxml"));
		root = loader.load();
		
		RegisterController registerController = loader.getController();
		registerController.getFocus();
		
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
