package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class namePanelController {
	@FXML private Label title;
	@FXML private Label namelabel;
	@FXML private Button Apply;
	@FXML private TextField NameField;
	
	@FXML 
	private void getNameInfo() {
		
			String NewName  = NameField.getText();
			System.out.println(NewName);
			setDisplayName(NewName);
	}

}
