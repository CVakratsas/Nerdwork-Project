package application.gui.classes;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
//	@FXML
//    private HBox calendarPane;
//
//    @FXML
//    private void initialize() {
//    	DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
//    	Node calendar = datePickerSkin.getPopupContent();
//        calendarPane.getChildren().add(calendar);
//    }
	
	public void switchToLogin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToMyCourses(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/MyCourses.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToMyCoursesAddCourse(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/MyCoursesAddCourse.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
