package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class bioPanelController {
	@FXML private TextArea Enterbio;
	@FXML private Label title;
	@FXML private Button Apply;
	@FXML private Label Success;
	@FXML private Label Failure;
	
	@FXML 
	private void getBioinfo() {
		
		//adding action to the button
		
			System.out.println(Enterbio.getText());
			String bio = Enterbio.getText();
			if (bio.length()<=300) {
				Success.setVisible(true);
				Failure.setVisible(false);
			}else {
				Failure.setVisible(true);
			}
	  
    }
}
