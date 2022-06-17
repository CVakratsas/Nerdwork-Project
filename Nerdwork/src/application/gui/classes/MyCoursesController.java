package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import application.functionality.Course;
import application.functionality.GuiController;
import application.functionality.Professor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
			myCourses = GuiController.getInstance().getEnrolledCourses();
			load(myCourses);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			System.out.println("Could not instantiate GuiController");
		}
	}
	
	public void load(ArrayList<Course> array) {
		
		int counter = array.size();
		
		// For every course
		for (int i = 0; i < counter; i++) {
			// Setting i as a final variable for the event handler
			final int temp_i = i;
			
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
			courseBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					try {
						new CourseProfileController().switchToCourseProfile(arg0, array.get(temp_i));
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
				}
				
			});
			
			// Setting style of the courseBox
			courseBox.setStyle("-fx-cursor: hand");
			
			// Button creation
			Button deleteButton = new Button("-");
			deleteButton.setFont(Font.font("Sefoe UI", FontWeight.BOLD, 14.0));
			deleteButton.setPrefSize(30, 30);
			deleteButton.setMinSize(30, 30);
			deleteButton.setMaxSize(30, 30);
			
			// Assign EventHandler to deleteButton
			final int tempI = i;
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					try {
						GuiController.getInstance().courseDisenrollment(array.get(tempI).getId());
					} catch (IOException | ParseException e) {
						System.out.println("Error occured when the couseDisenrollment method was invoked");
						e.printStackTrace();
					}
				}
				
			});
			
			// VBox for the button
			VBox buttonBox = new VBox(deleteButton);
			buttonBox.setAlignment(Pos.CENTER);
			
			// Adding the whole row
			RowConstraints row = new RowConstraints(80);
			coursesPane.getRowConstraints().add(row);
			
			coursesPane.add(courseBox, 0, i + 1);
			coursesPane.add(buttonBox, 1, i + 1);
			coursesPane.setMargin(courseBox, new Insets(0, 0, 0, 10));
		}
	}
	
	public void switchToMyCoursesAddCourse(ActionEvent event) {
		Navigator.loadCenter(Navigator.MyCoursesAddCourse);
	}
}
