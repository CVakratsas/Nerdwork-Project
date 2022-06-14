package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import application.functionality.Course;
import application.functionality.GuiController;
import application.functionality.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyCoursesAddCourseController {
	
	private ArrayList<Course> courses;
	
	@FXML
	private GridPane coursesPane;
	
	@FXML
	private void initialize() throws IOException, ParseException {
		GuiController.getInstance().login("probatos", "beeeH1234@");
		courses = GuiController.getInstance().getAllCourses();
		load(courses);
	}
	
	public void load(ArrayList<Course> array) {
		int counter = array.size();
		for (int i = 0; i < counter; i++) {
			// Labels creation
			Label labelCourse = new Label(courses.get(i).getName());
			
			// Get all professors
			ArrayList<Professor> profs = courses.get(i).getProfessors();
			String profsString = profs.get(0).getDisplayName();
			for(int x=1; x<profs.size()-1; x++) {
				profsString += ", " + profs.get(x).getDisplayName();
			}
			Label labelProf = new Label(profsString);
			
			labelCourse.setFont(Font.font("Segoe UI", 18.0));
			labelProf.setFont(Font.font("Segoe UI", 16.0));
			
			// VBox creation
			VBox courseBox = new VBox(labelCourse, labelProf);
			courseBox.setAlignment(Pos.CENTER_LEFT);
			courseBox.setPrefHeight(200.0);
			courseBox.prefWidth(100.0);
			
			// VBox button creation
			Button addButton = new Button("+");
			addButton.setFont(Font.font("Sefoe UI", FontWeight.BOLD, 14.0));
			VBox buttonBox = new VBox(addButton);
			addButton.setPrefSize(30, 30);
			addButton.setMinSize(30, 30);
			addButton.setMaxSize(30, 30);
			buttonBox.setAlignment(Pos.CENTER);
			
			// Adding
			RowConstraints row = new RowConstraints(80);
			coursesPane.getRowConstraints().add(row);
			
			coursesPane.add(courseBox, 0, i + 1);
			coursesPane.add(buttonBox, 1, i + 1);
			coursesPane.setMargin(courseBox, new Insets(0, 0, 0, 10));
		}
	}
	
	@FXML
	public void switchToMyCourses(ActionEvent event) {
		Navigator.loadCenter(Navigator.MyCourses);
	}
}
