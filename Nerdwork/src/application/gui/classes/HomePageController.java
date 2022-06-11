package application.gui.classes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageController {
	
	// TODO
	// Create a variable that can distinguish a user inside the database e.g. id
	private Stage stage;
	private Scene scene;
	private Parent root;
	private ArrayList<Course> courses;
	
	@FXML
	private VBox calendarPane;
	
//	public ArrayList<Course> getArray() {
//		return courses;
//	}
	
	@FXML
	private void initialize() {
    	DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendar = datePickerSkin.getPopupContent();
        calendar.setStyle("-fx-background-color: #3889c4");
        calendarPane.getChildren().add(calendar);
    }
	
//	public void switchToMyCourses(ActionEvent event) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MyCourses.fxml"));
//		root = loader.load();
//		
//		MyCoursesController myCoursesController = loader.getController();
//		courses = new ArrayList<Course>();
//		Course c1 = new Course("smrid21037", "Αλγόριθμοι", "Σαμαράς");
//		Course c2 = new Course("smrid21085", "Προγραμματισμός Διαδικτύου", "Κασκάλης");
//		Course c3 = new Course("smrid21022", "Δίκτυα", "Τρακατέλης");
//		courses.add(c1);
//		courses.add(c2);
//		courses.add(c3);
//		myCoursesController.load(courses);
//		// TODO
////		GeneralController generalController = new GeneralController();
////		myCoursesController.load(generalController.getCourses());
//		
//		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
//	}
	
}

class Course {
	private String id;
	private String name;
	private String professor;
	
	public Course(String id, String name, String professor) {
		this.id = id;
		this.name = name;
		this.professor = professor;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProfessor() {
		return professor;
	}
}
