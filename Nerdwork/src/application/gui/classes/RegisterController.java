package application.gui.classes;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class RegisterController {
	
	@FXML
	Pane pane;
	
	public void getFocus() {
		pane.requestFocus();
	}
	
}
