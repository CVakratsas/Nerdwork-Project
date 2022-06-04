package application.gui.classes;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyCoursesController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private GridPane coursesPane;
	
	public void load(ArrayList<Course> array) {
		
		int counter = array.size();
		for (int i = 0; i < counter; i++) {
			// Labels creation
			Label labelCourse = new Label(array.get(i).getName());
			Label labelProf = new Label(array.get(i).getProfessor());
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
	
	public void switchToMyCoursesAddCourse(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MyCoursesAddCourse.fxml"));
		root = loader.load();
		
		HomePageController homePageController = new HomePageController();
		
		MyCoursesAddCourseController myCoursesAddCourseController = loader.getController();
		
		ArrayList<Course> coursesToAdd = new ArrayList<Course>();
		
		Course c1 = new Course("rmsid20551", "Ψηφιακή Οικονομία", "Στεϊκάκης");
		Course c2 = new Course("rmsid20384", "Τεχνολογία Λογισμικού", "Χατζηγεωργίου");
		Course c3 = new Course("rmsid20917", "Δομές Δεδομένων", "Σατρατζέμη");
		coursesToAdd.add(c1);
		coursesToAdd.add(c2);
		coursesToAdd.add(c3);
		
		myCoursesAddCourseController.load(coursesToAdd);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
