package application.gui.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RegisterController {
	
	@FXML
	Pane pane;
	
	@FXML
	ImageView close;
	
	public void getFocus() {
		pane.requestFocus();
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
