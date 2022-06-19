package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class mainController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML private Button ChangePassword;
	@FXML private Button ChangeBio;
	@FXML private Button ChangeName;
	@FXML private Button ChangeDirection;
	@FXML private BorderPane borderPane;
	@FXML private GridPane gridPane;
	
	
	
	
	public void ToPassword(ActionEvent event) throws IOException{
		ChangePassword.setStyle("-fx-font-weight: bold;");
		Parent root = FXMLLoader.load(getClass().getResource("PasswordPanel.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	
	
	}
	public void ToBio(ActionEvent event) throws IOException {
		ChangeBio.setStyle("-fx-font-weight: bold;");
		Parent root = FXMLLoader.load(getClass().getResource("bioPanel.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void ToNamePanel(ActionEvent event) throws IOException {
		ChangeName.setStyle("-fx-font-weight: bold;");
		Parent root = FXMLLoader.load(getClass().getResource("namePanel.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void ToDirection(ActionEvent event) throws IOException {
		ChangeDirection.setStyle("-fx-font-weight: bold;");
		Parent root = FXMLLoader.load(getClass().getResource("DirectionPanel.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
