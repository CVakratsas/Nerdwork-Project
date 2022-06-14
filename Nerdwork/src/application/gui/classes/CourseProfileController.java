package application.gui.classes;

import java.io.IOException;
import org.controlsfx.control.Rating;
import org.json.simple.parser.ParseException;

import application.functionality.Course;
import application.functionality.GuiController;
import application.functionality.Professor;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CourseProfileController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Course course;
	private GuiController controller;
	private static final String dbURL = "https://nerdnet.geoxhonapps.com/cdn/profPhotos/";
	
	@FXML
	private ImageView profilePicture;
	@FXML
	private Label name;
	@FXML
	private VBox professorList;
	@FXML
	private Label semester;
	@FXML
	private Label orientation;
	@FXML
	private Label ects;
	@FXML
	private TextFlow description;
	@FXML
	private Rating rating;
	
	
	
	private void setProfileDescription() throws IOException, ParseException {
		name.setText(course.getName());
		rating.setRating(course.getRating());
		semester.setText(Integer.toString(course.getSemester()) + "ο Εξάμηνο");;
		orientation.setText(course.getOrientation());
		ects.setText(Integer.toString(course.getECTS()) + " ECTS");
		description.getChildren().add(new Label(course.getDescription()));
		
		
		for(Professor p : course.getProfessors()) {
			Image img = new Image(dbURL + p.getProfilePhoto());
			ImageView picture = new ImageView(img);
			
			picture.setFitWidth(32);
			picture.setPreserveRatio(true);
			
			Label name = new Label(p.getDisplayName());
			name.setFont(new Font(18));
			
			HBox box = new HBox();
			box.getChildren().add(picture);
			box.getChildren().add(name);
			box.setId(p.getUserId());

			box.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				try {
					new ProfessorProfileController().switchToProfessorProfile(event, controller, controller.getProfessorByID(box.getId()));
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			});
			
			professorList.getChildren().add(box);
		}
	}
	
	
	
	public void load(Course c, GuiController controller) throws IOException, ParseException {
		this.course = c;
		this.controller = controller;
		setProfileDescription();
		
	}
	
	
	
	public void switchToCourseProfile(Event event, GuiController controller, Course c) throws IOException, ParseException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CourseProfile.fxml"));
		root = loader.load();
		CourseProfileController courseProfileController = loader.getController();
		
		courseProfileController.load(c, controller);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
