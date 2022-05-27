package application.gui.classes;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.VBox;

public class HomePageController {
	
	@FXML
	private VBox calendarPane;
	
	@FXML
	private void initialize() {
    	DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendar = datePickerSkin.getPopupContent();
        calendar.setStyle("-fx-background-color: #3889c4");
        calendarPane.getChildren().add(calendar);
    }
}
