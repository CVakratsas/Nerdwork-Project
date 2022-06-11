package application.gui.classes;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("../fxml/HomePage.fxml"));
//			Scene scene = new Scene(root);
			stage.setScene(createScene(loadHomePage()));
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
	
	private BorderPane loadHomePage() throws IOException {
	      FXMLLoader loader = new FXMLLoader();

	      BorderPane mainPane = (BorderPane) loader.load(getClass().getResourceAsStream(Navigator.MenuBars));

	      MenuBarsController mainController = loader.getController();

	      Navigator.setMainController(mainController);
	      Navigator.loadCenter(Navigator.HomePage);

	      return mainPane;
	}
	
	private Scene createScene(Pane mainPane) {
	      Scene scene = new Scene(mainPane);

//	      scene.getStylesheets().setAll(
//	          getClass().getResource("fullpackstyling.css").toExternalForm()
//	      );

	      return scene;
	}
}
