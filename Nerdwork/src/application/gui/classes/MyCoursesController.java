package application.gui.classes;

import java.util.ArrayList;

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
			RowConstraints row = new RowConstraints(80);
			coursesPane.getRowConstraints().add(row);
			
			coursesPane.add(courseBox, 0, i + 2);
			coursesPane.add(buttonBox, 1, i + 2);
			coursesPane.setMargin(courseBox, new Insets(0, 0, 0, 10));
		}
	}
}
