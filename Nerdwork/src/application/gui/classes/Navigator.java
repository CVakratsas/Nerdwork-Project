package application.gui.classes;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class Navigator {
	public static final String MenuBars = "/application/gui/fxml/MenuBars.fxml";
    public static final String MyCourses = "/application/gui/fxml/MyCourses.fxml";
    public static final String MyCoursesAddCourse = "/application/gui/fxml/MyCoursesAddCourse.fxml";
    public static final String HomePage = "/application/gui/fxml/HomePage.fxml";

    private static MenuBarsController mainController;

    public static void setMainController(MenuBarsController mainController) {
        Navigator.mainController = mainController;
    }

    public static void loadCenter(String fxml) {
        try {
        	mainController.setCenter(
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
