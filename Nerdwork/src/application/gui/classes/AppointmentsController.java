package application.gui.classes;

import java.io.IOException;
import java.util.Date;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.Professor;
import application.functionality.Timeslot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AppointmentsController {
	
	private GuiController controller;
	private Professor currentProfessor;
	private static final String dbURL = "https://nerdnet.geoxhonapps.com/cdn/profPhotos/";

	
	@FXML
	private ImageView currentProfessorPicture;
	@FXML
	private Label currentProfessorName;
	@FXML
	private Label currentProfessorOffice;
	@FXML
	private VBox professorList;
	
	
	public void initialize() throws IOException, ParseException {
		controller = GuiController.getInstance();
		
		setContents();
	}
	
	
	public void setContents() throws IOException, ParseException {
		
		//Loads all Professors
		for(Professor p : controller.getAllProfessors()) {
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
			
			
			//Loads selected Professor's available appointments
			box.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				try {
					Professor chosenProfessor = controller.getProfessorById(Integer.parseInt(box.getId()));
					currentProfessorPicture.setImage(new Image(dbURL + chosenProfessor.getProfilePhoto()));
					currentProfessorName.setText(chosenProfessor.getDisplayName());
					currentProfessorOffice.setText(chosenProfessor.getOffice());
					
					loadAppointments(chosenProfessor);
					
				} catch (NumberFormatException | IOException | ParseException e) {e.printStackTrace();}
			});
			
			professorList.getChildren().add(box);
		}
	}
	
	
	
	private void loadAppointments(Professor p) throws IOException, ParseException {
		for(Timeslot t : controller.getAvailableTimeslots(p)) {
			System.out.println(new Date(((long) t.getStartHourTimestamp()) * 1000));
			System.out.println(new Date(((long) t.getEndHourTimestamp()) * 1000));
			System.out.println("\n");
		}
	}
}
