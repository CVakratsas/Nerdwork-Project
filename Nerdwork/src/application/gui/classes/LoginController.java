package application.gui.classes;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import application.functinonality.GuiController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button signInButton;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ImageView close;
	
	public void login(ActionEvent event) throws IOException, ParseException {
		
		GuiController guiController = new GuiController();
		boolean answer = guiController.login(username.getText(), password.getText());
		
		if(answer) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(createScene(loadHomePage()));
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(null);
			alert.setHeaderText("Invalid Iput Data");
			alert.setContentText("Credentials do not match with an account");
			alert.initStyle(StageStyle.UTILITY);
			alert.showAndWait();
		}
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
	
	public void switchToRegister(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.RegisterPage));
		root = loader.load();
				
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
	public void closeWindow() {
		
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
	        @Override
	        public void handle(MouseEvent event) {
	            Platform.exit();
	            event.consume();
	        }
	   });
	}
}
