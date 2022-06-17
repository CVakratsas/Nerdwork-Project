package application.gui.classes;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Navigator {
	
	public static final String LoginPage = "/application/gui/fxml/Login.fxml";
	public static final String RegisterPageStudent = "/application/gui/fxml/RegisterAsStudent.fxml";
	public static final String RegisterPageProfessor = "/application/gui/fxml/RegisterAsProfessor.fxml";
	public static final String MenuBars = "/application/gui/fxml/MenuBars.fxml";
    public static final String MyCourses = "/application/gui/fxml/MyCourses.fxml";
    public static final String MyCoursesAddCourse = "/application/gui/fxml/MyCoursesAddCourse.fxml";
    public static final String HomePage = "/application/gui/fxml/HomePage.fxml";
    public static final String ProfessorProfile = "/application/gui/fxml/ProfessorProfile.fxml";
    public static final String CourseProfile = "/application/gui/fxml/CourseProfile.fxml";
    public static final String Settings = "/application/gui/fxml/Settings.fxml";
    public static final String Appointments = "/application/gui/fxml/Appointments.fxml";

    private static MenuBarsController menuBarsController;

    public static void setMainController(MenuBarsController mainController) {
        Navigator.menuBarsController = mainController;
    }

    public static void loadCenter(String fxml) {
        try {
        	
        	menuBarsController.setCenter(
                    FXMLLoader.load(
                        Navigator.class.getResource(
                            fxml
                        )
                    )
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void setCenter(FXMLLoader loader) throws IOException {
    	menuBarsController.setCenter(loader.load());
    }
}
