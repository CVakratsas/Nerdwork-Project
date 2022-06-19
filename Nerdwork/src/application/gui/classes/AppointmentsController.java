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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
		
		setContentView();
	}
	
	
	public void setContentView() throws IOException, ParseException {
		
		//Loads all Professors
		for(Professor p : controller.getAllProfessors()) {
			Image img = new Image(GuiController.dbURL + p.getProfilePhoto());
			ImageView picture = new ImageView(img);
			
			picture.setFitWidth(32);
			picture.setPreserveRatio(true);
			
			Label name = new Label(p.getDisplayName());
			name.setFont(new Font(18));
			
			HBox box = new HBox();
			box.getChildren().add(picture);
			box.getChildren().add(name);
			box.setStyle("-fx-cursor: hand");
			
			
			//Loads selected Professor's available appointments
			box.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				try {
					currentProfessor = p;
					currentProfessorPicture.setImage(new Image(GuiController.dbURL + p.getProfilePhoto()));
					currentProfessorName.setText(currentProfessor.getDisplayName());
					currentProfessorName.setStyle("-fx-cursor: hand");
					currentProfessorOffice.setText("Γραφείο: " + currentProfessor.getOffice());
					
					
					//Loads selected Professor profile
					currentProfessorName.addEventHandler(MouseEvent.MOUSE_CLICKED, (event2) -> {
						try {
							new ProfessorProfileController().switchToProfessorProfile(event2, currentProfessor);
						} catch (NumberFormatException | IOException | ParseException e) {e.printStackTrace();}
					});
					
					
					loadAppointments();
				} catch (NumberFormatException | IOException | ParseException e) {e.printStackTrace();}
			});
			
			professorList.getChildren().add(box);
		}
	}	
	

	private void loadAppointments() throws IOException, ParseException {
		appointmentList.getChildren().clear();

		ArrayList<Timeslot> timeslots = controller.getAvailableTimeslots(currentProfessor);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone(GuiController.Timezone));

		
		//Professor has available appointments
		if(timeslots != null) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(GuiController.Timezone));
			
			
			while(!timeslots.isEmpty())
				for(String day : Timeslot.Days) {
	
					//All Timeslots of the day
					ArrayList<Timeslot> dayTimeslots = new ArrayList<>();
	
					
					//Selects all Timeslots of a certain day of the week
					for(Timeslot t : timeslots) {
						Date startHourDate = new Date(t.getStartHourTimestampMili());
						calendar.setTime(startHourDate);
						
						
						String weekDay = Timeslot.Days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
						if(!weekDay.equals(day))
							break;
						
						dayTimeslots.add(t);
					}
					
					
					//Removes selected Timeslots from all Timeslots
					for(Timeslot t : dayTimeslots) {
						timeslots.remove(t);
					}
				
					
					//No appointment was found for a certain day
					if(dayTimeslots.isEmpty())
						continue;
					else {
						Date startHourDate = new Date(((long) dayTimeslots.get(0).getStartHourTimestamp()) * 1000);
						calendar.setTime(startHourDate); //Sets current Date
					}
						
					
					//Creates a new Container to render all available appointments for all available dates
					VBox appointment = new VBox();
					appointment.setSpacing(15);
					appointment.setAlignment(Pos.TOP_CENTER);
					
					
					//Loads Date and Day
					Label date = new Label(formatter.format(calendar.getTime()));
					Label weekDay = new Label(Timeslot.Days[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
					date.setFont(new Font(14));
					weekDay.setFont(new Font(16));
					appointment.getChildren().add(date);
					appointment.getChildren().add(weekDay);
					
					
					for(Timeslot t : dayTimeslots) {
							
						//Calculates the appointment's starting time and formats it properly
						Date startDate = new Date(t.getStartHourTimestampMili());
						calendar.setTime(startDate);
						String startTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
						
						
						//Calculates the appointment's ending time and formats it properly
						Date endDate = new Date(t.getEndHourTimestampMili());
						calendar.setTime(endDate);
						String endTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
						
						
						String timeRange = startTime + " - " + endTime;
						Button appointmentButton = new Button(timeRange);
						
						
						//Button Color based on Timeslot Availability
						switch(t.getStatus()) { 
						
							case 3:
								appointmentButton.setStyle("-fx-background-color: limegreen");
								break;
							case 0:
								appointmentButton.setStyle("-fx-background-color: #FFE5B4");
								appointmentButton.setDisable(true);
								break;
							default:
								appointmentButton.setStyle("-fx-background-color: darkred");
								appointmentButton.setDisable(true);
								break;
						}
						
						
						//Button event handler to request an appointment
						appointmentButton.addEventHandler(ActionEvent.ACTION, (event) -> {
							Timeslot selectedTimeslot = t;
							try {
								boolean response = controller.requestAppointment(currentProfessor, selectedTimeslot);
								
								if(response)
									GuiController.getInstance().
									alertFactory("Επιτυχής Καταχώρηση Ραντεβού",
													"Το ραντεβού σας με τον καθηγητή "
													+ currentProfessor.getDisplayName()
													+ " καταχωρήθηκε με επιτυχία.");
								else
									GuiController.getInstance().
									alertFactory("Ανεπιτυχής Καταχώρηση Ραντεβού",
													"Η καταχώρηση του ραντεβού σας με τον καθηγητή "
													+ currentProfessor.getDisplayName()
													+ " απέτυχε.");
								
								loadAppointments();
							} catch (IOException | ParseException e) {
								e.printStackTrace();
							}
						});
						
						
						appointment.getChildren().add(appointmentButton);
					}
					
					
					//Lists all appointments for the day
					appointmentList.getChildren().add(appointment);
				}

			double spacing = appointmentList.getPrefWidth() / (Math.pow(appointmentList.getChildren().size(), 2));
			appointmentList.setSpacing(spacing);
		}
	}
}
