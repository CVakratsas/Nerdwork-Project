package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

public class PasswordPanelController {
	@FXML private Label title;
	@FXML private Label oldPass;
	@FXML private Label newPass;
	@FXML private Label confirmPass;
	@FXML private TextField oldPassField;
	@FXML private TextField newPassField;
	@FXML private TextField confirmPassField;
	@FXML private Button Apply;
	@FXML private Label Success;
	@FXML private Label Failure;
	@FXML private Label Missing;
	
	@FXML 
	private void getPasswordfromUser(){
			
		String oldPassword=oldPassField.getText();
		String newPassword=newPassField.getText();
		String confirmPassword= confirmPassField.getText();
		System.out.println (oldPassword);
		if(oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
			Missing.setVisible(true);
		}else {
			Missing.setVisible(false);
			if(newPassword.equals(confirmPassword)) {
			   Success.setVisible(true);
			   Failure.setVisible(false);
		       changePassword(oldPassword,newPassword);
		    }else {
			   Failure.setVisible(true);
		    }
		}
	}
}
