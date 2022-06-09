package application.gui.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MenuController {
	
	@FXML
	ImageView close;
	
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
