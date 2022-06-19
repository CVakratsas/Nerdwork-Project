package application.gui.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/*
 *Use cases 4 and 7 were not developed.
 *The required libraries are not located in the repository due to file size exceding the limit of GitHub.
 */

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(Navigator.LoginPage));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Nerdwork");
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
