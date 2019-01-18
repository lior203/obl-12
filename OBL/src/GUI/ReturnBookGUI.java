package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.w3c.dom.css.Counter;

import Client.Client;
import Common.GuiInterface;
import Common.Member;
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
import logic.CommonController;
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
	private Member memb;
	private String oldStatus;


	//	@FXML
	//	void memberKeyPressed(KeyEvent event) {
	//		if (event.getCode()==KeyCode.ENTER) {
	//			try {
	//				RegistrationController.checkMemberExistence(txtMember_ID.getText());
	//			} catch (Exception e) {
	//				showFailed(e.getMessage());
	//			}
	//		}
	//	}

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
		if(txtCopy_ID.getText().equals(copyID)) {
			if(memb.getStatus().equals("Active")) {
				BookHandlerController.returnBook(txtCopy_ID.getText(),memb.getStatus());
			}
			else {
				
				if(txtCopy_ID.getText().equals(memb.getFreezedOn())) {
					BookHandlerController.isMemberLateOnReturn(memb.getId());
				}
				else {
					BookHandlerController.returnBook(txtCopy_ID.getText(),memb.getStatus());
				}
			}
		}
		else {
			Platform.runLater(() -> {
				showFailed("You changed the Copy ID field, to continue click enter in Copy ID field");
				btnReturn.setDisable(true);
			});
		}
		btnReturn.setDisable(true);
	}


	@Override
	public void display(Object obj) {
		ArrayList<String> msg = (ArrayList<String>)obj;
		switch (msg.get(0)) {
		case "Check Member Existence":
			memb = new Member(msg.get(1), msg.get(2), msg.get(3), msg.get(4), msg.get(5), 
					msg.get(6),msg.get(7), msg.get(8), msg.get(9), msg.get(10), msg.get(11), msg.get(12));
			txtMember_Status.setText(memb.getStatus());
			txtFirst_Name.setText(memb.getFirstName());
			txtLast_name.setText(memb.getLastName());
			btnReturn.setDisable(false);
			copyID = txtCopy_ID.getText();
			oldStatus = memb.getStatus();
			break;

		case "Check Copy Loan Status":
			txtMember_ID.setText(msg.get(4));
			try {
				CommonController.checkMemberExistence(msg.get(4));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			break;

		case "Check Copy ID Existence":
			try {
				txtBook_Name.setText(msg.get(2));
				txtReturn_Date.setText(msg.get(5));
				BookHandlerController.isCopyLoaned(txtCopy_ID.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "Return Book":
			txtReturn_Date.setText(msg.get(1));
			Platform.runLater(() -> {
				if(oldStatus.equals(memb.getStatus())) {
					showSuccess("Copy of the book " + txtBook_Name.getText() + " returned successfully");
				}
				else {
					showSuccess("Copy of the book " + txtBook_Name.getText() + " returned successfully and the member status is now " + memb.getStatus());
				}
			});
			break;

		case "Check If Member Is Late On Return":
			if(Integer.parseInt(((ArrayList<String>)msg).get(1)) == 1) {
				if(memb.getIsGraduated().equals("true")) {
					CommonController.changeMemberStatus(memb.getId(), "Locked");	

				}
				else {
					CommonController.changeMemberStatus(memb.getId(), "Active");	
				}
				BookHandlerController.returnBook(txtCopy_ID.getText(),oldStatus);
			}
			else {
				BookHandlerController.returnBook(txtCopy_ID.getText(),memb.getStatus());
			}
			break;

		case "Change Member Status":
			Platform.runLater(() -> {
			if(msg.size() == 1) {
					showFailed("Failed to change member status");
			}
			else {
				memb.setStatus(msg.get(1));
			}
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
