package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import application.functinonality.Course;
import application.functinonality.GuiController;
import application.functinonality.Professor;
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

public class MyCoursesController {
	
	private ArrayList<Course> myCourses;
	
	@FXML
	GridPane coursesPane;
	
	@FXML
	private void initialize() {
		try {
			GuiController guiController = new GuiController();
			guiController.login("probatos", "beeeH1234@");
			myCourses = guiController.getEnrolledCourses();
			load(myCourses);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			System.out.println("Could not instantiate GuiController");
		}
	}
	
	public void load(ArrayList<Course> array) {
		int counter = array.size();
		for (int i = 0; i < counter; i++) {
			// Labels creation
			Label labelCourse = new Label(myCourses.get(i).getName());
			
			// Get all professors
			ArrayList<Professor> profs = myCourses.get(i).getProfessors();
			String profsString = "";
			for(Professor prof: profs) {
				profsString += prof.getDisplayName();
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
			Button deleteButton = new Button("-");
			deleteButton.setFont(Font.font("Sefoe UI", FontWeight.BOLD, 14.0));
			deleteButton.setPrefSize(30, 30);
			deleteButton.setMinSize(30, 30);
			deleteButton.setMaxSize(30, 30);
			VBox buttonBox = new VBox(deleteButton);
			buttonBox.setAlignment(Pos.CENTER);
			
			// Adding
			RowConstraints row = new RowConstraints(80);
			coursesPane.getRowConstraints().add(row);
			
			coursesPane.add(courseBox, 0, i + 2);
			coursesPane.add(buttonBox, 1, i + 2);
			coursesPane.setMargin(courseBox, new Insets(0, 0, 0, 10));
		}
	}
	
	public void switchToMyCoursesAddCourse(ActionEvent event) {
		Navigator.loadCenter(Navigator.MyCoursesAddCourse);
	}
}
