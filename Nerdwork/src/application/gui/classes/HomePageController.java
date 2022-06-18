package application.gui.classes;

import java.io.IOException;
import java.time.LocalDate;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HomePageController {
	
	@FXML
	private Label displayName, email, orientation;
	@FXML
	private VBox calendarPane;
	@FXML
	private TextFlow bio;
	
	@FXML
	private void initialize() throws IOException, ParseException {
		
		// Initialize calendar
    	DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendar = datePickerSkin.getPopupContent();
        calendar.setStyle("-fx-background-color: #3889c4");
        calendarPane.getChildren().add(calendar);
        Text bioText = new Text(GuiController.getInstance().getUser().getBio());
        bioText.setFont(Font.font("Segoe UI", 16.0));
        bio.getChildren().add(bioText);
        
        // Initialize user's information
        displayName.setText(GuiController.getInstance().getUser().getDisplayName());
        email.setText(GuiController.getInstance().getUser().getEmail());
        int orientationInt = GuiController.getInstance().getUser().getOrientation();
        if(orientationInt == 0) {
        	orientation.setText("Επιστήμη και\nΤεχνολογία\nΥπολογιστών");
        }
        else if(orientationInt == 1) {
        	orientation.setText("Πληροφοριακά\nΣυστήματα");
        }
        else {
        	orientation.setText(""); // When the professor is logged in nothing is displayed
        }
    }	
}
