package application.gui.classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.Professor;
import application.functionality.Timeslot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	@FXML
	private HBox appointmentList;
	
	
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
					currentProfessor = controller.getProfessorById(Integer.parseInt(box.getId()));
					currentProfessorPicture.setImage(new Image(dbURL + currentProfessor.getProfilePhoto()));
					currentProfessorName.setText(currentProfessor.getDisplayName());
					currentProfessorOffice.setText(currentProfessor.getOffice());
					
					loadAppointments();
					
				} catch (NumberFormatException | IOException | ParseException | java.text.ParseException e) {e.printStackTrace();}
			});
			
			professorList.getChildren().add(box);
		}
	}	
	

	private void loadAppointments() throws IOException, ParseException, java.text.ParseException {
		appointmentList.getChildren().clear();
		
		ArrayList<Timeslot> timeslots = controller.getAvailableTimeslots(currentProfessor);
		appointmentList.setSpacing(15);
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		
		if(timeslots != null) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			
			
			
			while(!timeslots.isEmpty())
				for(String day : Timeslot.Days) {
	
					//Timeslots of the day
					ArrayList<Timeslot> dayTimeslots = new ArrayList<>();
	
					
					//Selects all timeslots of a certain day of the week
					for(Timeslot t : timeslots) {
						Date startHourDate = new Date(((long) t.getStartHourTimestamp()) * 1000);
						calendar.setTime(startHourDate);
						
						
						String weekDay = Timeslot.Days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
						if(!weekDay.equals(day))
							break;
						
						dayTimeslots.add(t);
					}
					
					
					//Removes selected timeslots from all timeslots
					for(Timeslot t : dayTimeslots) {
						timeslots.remove(t);
					}
					
					
					
					if(dayTimeslots.isEmpty()) //No appointment was found for a certain day
						continue;
					else {
						Date startHourDate = new Date(((long) dayTimeslots.get(0).getStartHourTimestamp()) * 1000);
						calendar.setTime(startHourDate);
					}
						
					
					//Creates a new Container to render all available appointments for all available dates
					VBox appointment = new VBox();
					appointment.setSpacing(15);
					
					
					String dateStr = formatter.format(calendar.getTime());
					String weekDayStr = Timeslot.Days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
					
					
					Label date = new Label(dateStr);
					Label weekDay = new Label(weekDayStr);
					
					appointment.getChildren().add(date);
					appointment.getChildren().add(weekDay);
					
					
					for(Timeslot t : dayTimeslots) {
						
						Date startDate = new Date(((long) t.getStartHourTimestamp()) * 1000);
						calendar.setTime(startDate);
						String startTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
						
						
						Date endDate = new Date(((long) t.getEndHourTimestamp()) * 1000);
						calendar.setTime(endDate);
						String endTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
						
						
						String timeRange = startTime + " - " + endTime;
						
						
						Button appointmentButton = new Button(timeRange);
						appointment.getChildren().add(appointmentButton);
					}
					
					appointmentList.getChildren().add(appointment);
				}
		}	
	}
}
