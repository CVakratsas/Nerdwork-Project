package application.gui.classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.simple.parser.ParseException;

import application.functionality.GuiController;
import application.functionality.Timeslot;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

public class HomePageController {
	
	private GuiController controller;
	
	@FXML
	private Label displayName, email, orientation;
	@FXML
	private VBox calendarPane;
	@FXML
	private TextFlow bio;
	@FXML
	private VBox availableAppointments;
	
	@FXML
	private void initialize() throws IOException, ParseException {
		
		// Initialize GuiController
		controller = GuiController.getInstance();
//		controller.setAvailableTimeslot(0, 16, 19);
//		controller.setAvailableTimeslot(2, 6, 8);
//		controller.setAvailableTimeslot(4, 21, 23);
//		controller.setAvailableTimeslot(5, 11, 14);
		
		
		// Initialize calendar
        loadCalendar();
        
        
        // Initialize user's information
        Text bioText = new Text(controller.getUser().getBio());
        bioText.setFont(Font.font("Segoe UI", 16.0));
        bio.getChildren().add(bioText);
        
        displayName.setText(controller.getUser().getDisplayName());
        email.setText(controller.getUser().getEmail());

        int orientationInt = controller.getUser().getOrientation();
        if(orientationInt == 0) {
        	orientation.setText("Επιστήμη και\nΤεχνολογία\nΥπολογιστών");
        }
        else if(orientationInt == 1) {
        	orientation.setText("Πληροφοριακά\nΣυστήματα");
        }
        else {
        	orientation.setText(""); // When the professor is logged in nothing is displayed
        }
    }	
	
	
	public void loadCalendar() throws IOException, ParseException {
		ArrayList<Timeslot> userTimeslots = controller.getRequestedAppointments();
		ArrayList<Date> dates = new ArrayList<>();

		
		for (Timeslot t : userTimeslots) {// Converts User's Timeslots to LocalDates
			Date date = new Date(t.getStartHourTimestampMili());
			dates.add(date);
		}

		
		//Formats Date and converts it to system Timezone
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		formatter.setTimeZone(TimeZone.getTimeZone(GuiController.Timezone));
		
		
		// Creates a new DatePicker with a custom DayCellFactory to highlight all User's Timeslots
		DatePicker datePicker = new DatePicker(LocalDate.now());
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell> () {

			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {

					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						
						
						// Clears available appointments tab whenever a Datecell is chosen
						availableAppointments.getChildren().clear();
						for (Date date : dates) {
							
							// Selected date exists in User's reserved dates
							if (item.compareTo(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) == 0) {
								this.setStyle("-fx-background-color: orange");

								
								// DateCell event handler to show all User dates of the selected Date
								this.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
									
									// Initially clears available appointments tab to avoid multiple entries
									if(dates.indexOf(date) == 0)
										availableAppointments.getChildren().clear();
									
									
									Label appointmentDate = new Label(formatter.format(date));
									Button cancelAppointment = new Button("X");
									
									
									// Button event handler to cancel an appointment
									cancelAppointment.addEventHandler(MouseEvent.MOUSE_CLICKED, (event2) -> {
										Timeslot t = userTimeslots.get(dates.indexOf(date));
										
											try {
												controller.rejectAppointmentRequested(t);
											} catch (IOException e) {
												e.printStackTrace();
											}
									});
									
									
									HBox appointment = new HBox();
									
									appointment.getChildren().add(appointmentDate);
									appointment.getChildren().add(cancelAppointment);
									availableAppointments.getChildren().add(appointment);
								});
							}
						}
					};
				};
			};
		};

		
		// Generates a visible Calendar for the GUI
		datePicker.setDayCellFactory(dayCellFactory);
		DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
		Node calendar = datePickerSkin.getPopupContent();
		calendar.setStyle("-fx-background-color: #3889c4");
		calendarPane.getChildren().add(calendar);
	}
}
