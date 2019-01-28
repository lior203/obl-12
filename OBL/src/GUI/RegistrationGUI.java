package GUI;


import java.net.URL;
import java.util.ResourceBundle;

import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import logic.Main;
import logic.RegistrationController;

public class RegistrationGUI implements Initializable, GuiInterface{
	@FXML
	private AnchorPane Registration;

	@FXML
	private Button btnSave;

	@FXML
	private TextField txtPhone_number;

	@FXML
	private TextField txtID;

	@FXML
	private TextField txtLast_name;

	@FXML
	private TextField txtFirst_name;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPassword;


	@FXML
	void onSaveClick(ActionEvent event) {
		if (checkfields())
			showFailed("Fill all the dields");
		else {
			RegistrationController.registration(txtPhone_number.getText(),txtID.getText(),txtLast_name.getText(),txtFirst_name.getText(),txtEmail.getText(),txtPassword.getText());
		}

	}


	private boolean checkfields() {
		if ((txtEmail.getText()).equals(""))
			return true;
		if ((txtFirst_name.getText()).equals(""))
			return true;
		if ((txtID.getText()).equals(""))
			return true;
		if ((txtLast_name.getText()).equals(""))
			return true;
		if ((txtPassword.getText()).equals(""))
			return true;
		if ((txtPhone_number.getText()).equals(""))
			return true;
		return false;
	}


	@Override
	public void showSuccess(String string) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(string);
		alert.showAndWait();	
	}


	@Override
	public void display(Object obj) {
		// TODO Auto-generated method stub

	}


	@Override
	public void showFailed(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Message");
		alert.setHeaderText(message);
		alert.showAndWait();	
	}


	@Override
	public void freshStart() {
		txtID.setText("");
		txtFirst_name.setText("");
		txtLast_name.setText("");
		txtEmail.setText("");
		txtPhone_number.setText("");
		txtPassword.setText("");	
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.client.clientUI=this;	
	}

}
