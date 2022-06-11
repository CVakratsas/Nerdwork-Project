package application.gui.classes;

import java.util.ArrayList;
import java.util.Arrays;
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

public class MyCoursesAddCourseController {
	
	private ArrayList<Course> courses;
	
	@FXML
	private GridPane coursesPane;
	
	@FXML
	private void initialize() {
		// Try and catch to see if DB data where loaded
		try {
			GuiController guiController = new GuiController();
			guiController.login("example", "12345678");
			courses = guiController.getAllCourses();
			load(courses);
		}
		catch (Exception e){
			System.out.println("Did not load from DB");
			
			Professor p1 = new Professor("Σαμαράς Νικόλαος", 22, "sam@gmail.com", "thisTextIsAPhoto", "6987", "335", (float) 7.2);
			Professor p2 = new Professor("Σακελλαρίου Ηλίας", 22, "sam@gmail.com", "thisTextIsAPhoto", "6987", "335", (float) 7.2);
			Professor p3 = new Professor("Καρακασίδης Αλέξανδρος", 22, "sam@gmail.com", "thisTextIsAPhoto", "6987", "335", (float) 7.2);
			ArrayList<Professor> profsArray = new ArrayList<Professor>(Arrays.asList(p1,p2,p3));
			Course c1 = new Course("id11", "Ανάλυση Αλγορίθμων", new ArrayList<String>(Arrays.asList("id22", "id77")), (float) 7.2, 4, profsArray);
			Course c2 = new Course("id11", "Διαδικαστικός Προγραμματισμός", new ArrayList<String>(Arrays.asList("id22", "id77")), (float) 7.2, 4, profsArray);
			Course c3 = new Course("id11", "Μαθηματική Ανάλυση", new ArrayList<String>(Arrays.asList("id22", "id77")), (float) 7.2, 4, profsArray);
			ArrayList<Course> coursesArray = new ArrayList<Course>(Arrays.asList(c1,c2,c3));
			
			load(coursesArray);
		}
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
