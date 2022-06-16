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

public class MyCoursesAddCourseController {
	
	private ArrayList<Course> courses;
	
	@FXML
	private GridPane coursesPane;
	
	@FXML
	private void initialize() throws IOException, ParseException {
		courses = GuiController.getInstance().getAllCourses();
		loadCourses(courses);
	}
	
	public void loadCourses(ArrayList<Course> array) {
		
		int counter = array.size();
		// For every course
		for (int i = 0; i < counter; i++) {
			// Setting i as a final variable for the event handlers
			final int temp_i = i;
			
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
			
			// Course VBox creation
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
			
			// Settings style of the courseBox
			courseBox.setStyle("-fx-cursor: hand");
			
			// Button creation
			Button addButton = new Button("+");
			addButton.setFont(Font.font("Sefoe UI", FontWeight.BOLD, 14.0));
			addButton.setPrefSize(30, 30);
			addButton.setMinSize(30, 30);
			addButton.setMaxSize(30, 30);
			addButton.setId(array.get(i).getId());
			
			// Assign EventHandler to addButton
			addButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					try {
						//TODO
						System.out.println("This is: " + array.get(temp_i).getName());
						System.out.println(GuiController.getInstance().courseEnrollment(array.get(temp_i).getId()));
					} catch (IOException | ParseException e) {
						System.out.println("Error occured when the couseEnrollment method was invoked");
						e.printStackTrace();
					}
				}
				
			});
			
			// VBox for the button
			VBox buttonBox = new VBox(addButton);
			buttonBox.setAlignment(Pos.CENTER);
			
			// Adding the whole row
			RowConstraints row = new RowConstraints(80);
			coursesPane.getRowConstraints().add(row);
			
			coursesPane.add(courseBox, 0, i + 1);
			coursesPane.add(buttonBox, 1, i + 1);
			coursesPane.setMargin(courseBox, new Insets(0, 0, 0, 10));
		}
	}
	
	public void switchToMyCourses(ActionEvent event) {
		Navigator.loadCenter(Navigator.MyCourses);
	}
}
