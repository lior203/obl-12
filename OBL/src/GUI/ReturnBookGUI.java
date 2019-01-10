package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.w3c.dom.css.Counter;

import Client.Client;
import Common.GuiInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.BookHandlerController;
import logic.InventoryController;
import logic.Main;
import logic.RegistrationController;

public class ReturnBookGUI implements Initializable, GuiInterface {

	@FXML
	private TextField txtBook_Name;

	@FXML
	private TextField txtFirst_Name;

	@FXML
	private TextField txtCopy_ID;

	@FXML
	private TextField txtMember_Status;

	@FXML
	private TextField txtLast_name;

	@FXML
	private Button btnReturn;

	@FXML
	private TextField txtMember_ID;

	@FXML
	private TextField txtReturn_Date;
	
	private String copyID;


	@FXML
	void copyKeyPressed(KeyEvent event) {
		if (event.getCode()==KeyCode.ENTER) {
			try {
				BookHandlerController.isCopyExist(txtCopy_ID.getText());
			} catch (Exception e) {
				showFailed(e.getMessage());
			}
		}
	}

	@FXML
	void clickReturnButton(ActionEvent event) {
		if(txtMember_Status.getText().equals("Active")) {
			if(txtCopy_ID.getText().equals(copyID)) {
				BookHandlerController.returnBook(txtCopy_ID.getText());
			}
			else {
				Platform.runLater(() -> {
					showFailed("You changed the Copy ID field, to continue click enter in Copy ID field");
				});
			}
		}
		else {
			
		}
	}

	@Override
	public void display(Object obj) {
		ArrayList<String> msg = (ArrayList<String>)obj;
		switch (msg.get(0)) {
		case "Check Member Existence":
			txtMember_Status.setText(msg.get(7));
			txtFirst_Name.setText(msg.get(5));
			txtLast_name.setText(msg.get(6));
			btnReturn.setDisable(false);
			copyID = txtCopy_ID.getText();
			break;

		case "Check Copy Loan Status":
			txtMember_ID.setText(msg.get(4));
			try {
				RegistrationController.checkMemberExistence(msg.get(4));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			break;

		case "Check Copy ID Existence":
			try {
				txtBook_Name.setText(msg.get(2));
				txtReturn_Date.setText(msg.get(6));
				BookHandlerController.isCopyLoaned(txtCopy_ID.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "Return Book":
				txtReturn_Date.setText(msg.get(1));
				Platform.runLater(() -> {
					showSuccess("Copy of the book " + txtBook_Name.getText() + " Returned successfully");
				});
			break;

		default:
			break;
		}
	}

	@Override
	public void showFailed(String message) {
		freshStart();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occurred");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.client.clientUI = this;

	}

	@Override
	public void showSuccess(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("An successful operation");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void freshStart() {
		txtMember_ID.setText("");
		txtMember_Status.setText("");
		txtFirst_Name.setText("");
		txtLast_name.setText("");
		txtReturn_Date.setText("");
		txtBook_Name.setText("");
		btnReturn.setDisable(true);		
	}
}
