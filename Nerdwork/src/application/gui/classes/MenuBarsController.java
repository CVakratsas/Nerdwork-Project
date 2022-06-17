package application.gui.classes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MenuBarsController {

    @FXML
    private AnchorPane centerHolder;
    @FXML
    private ImageView close;

    public void setCenter(Node node) {
    	centerHolder.getChildren().setAll(node);
    }
    
	public void switchToMyCourses(ActionEvent event) {
		Navigator.loadCenter(Navigator.MyCourses);
    }
    
	public void switchToHomePage(ActionEvent event) {
		Navigator.loadCenter(Navigator.HomePage);
    } 
	
	public void switchToProfessorListForAppointments(ActionEvent event) {
		Navigator.loadCenter(Navigator.ProfessorListForAppointments);
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
