package application.gui.classes;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class Navigator {
	
	public static final String LoginPage = "/application/gui/fxml/Login.fxml";
	public static final String RegisterPage = "/application/gui/fxml/Register.fxml";
	public static final String MenuBars = "/application/gui/fxml/MenuBars.fxml";
    public static final String MyCourses = "/application/gui/fxml/MyCourses.fxml";
    public static final String MyCoursesAddCourse = "/application/gui/fxml/MyCoursesAddCourse.fxml";
    public static final String HomePage = "/application/gui/fxml/HomePage.fxml";

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
}
