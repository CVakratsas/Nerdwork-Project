package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DirectionPanelController {
	ObservableList<String> directList = FXCollections.observableArrayList("ету","пс");
	
	@FXML private ChoiceBox<String> SelectDirection;
	@FXML private Label direc;
	@FXML private Label title;
	@FXML private Button Apply;
	 
	@FXML 
	private void initialize() {
		 SelectDirection.setItems(directList);
	}
	
	@FXML 
	private void getDirection() {
	
	//adding action to the button
		System.out.println(SelectDirection.getValue());
			
	
	}
	
	
}
