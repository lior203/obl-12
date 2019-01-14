package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import Common.GuiInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.Main;
import logic.RegistrationController;

public class MemberCardGUI implements Initializable,GuiInterface{

    @FXML
    private TextField txtMember_ID;

    @FXML
    private TextField txtFirst_Name;

    @FXML
    private TextField txtLast_Name;

    @FXML
    private TextField txtPhone_Number;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnLates_Lostbook;

    @FXML
    private TextArea txtArea_Notes;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox cmbStatus;
	ObservableList<String> list;
    @FXML
    void searchMember(KeyEvent event) {
    	if (event.getCode()==KeyCode.ENTER) {
			RegistrationController.searchMember(txtMember_ID.getText());
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Client.clientUI=this;
		setMsStatusComboBox();		
	}

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object obj) {		
		ArrayList<String>memberData=(ArrayList<String>) obj;
		setCardMember(memberData);
		setFields(false);
		if (memberData.get(0).equals("SearchMember")) {
			setEditableLibrarian();
		}
		else if (memberData.get(0).equals("SearchMemberLibrarianManager")) {
			//if librarian manager open card reader page - can change member status
			setEditableLibrarianManager();
		}

	}



	private void setEditableLibrarian() {
		txtFirst_Name.setEditable(false);
		txtLast_Name.setEditable(false);
		txtPhone_Number.setEditable(false);
		txtEmail.setEditable(false);
		txtArea_Notes.setEditable(true);
		cmbStatus.setEditable(false);
		btnSave.setDisable(true);//librarian cannot edit details
	}
	private void setEditableLibrarianManager() {
		txtFirst_Name.setEditable(false);
		txtLast_Name.setEditable(false);
		txtPhone_Number.setEditable(false);
		txtEmail.setEditable(false);
		txtArea_Notes.setEditable(true);
		cmbStatus.setEditable(true);
	}
	private void setCardMember(ArrayList<String> memberData) {
		txtFirst_Name.setText(memberData.get(5));
		txtLast_Name.setText(memberData.get(6));
		txtPhone_Number.setText(memberData.get(2));
		txtEmail.setText(memberData.get(3));
		txtArea_Notes.setText(memberData.get(8));
		cmbStatus.setValue(memberData.get(7));
	}

	private void setFields(Boolean cond) {
		txtFirst_Name.setDisable(cond);
		txtLast_Name.setDisable(cond);
		txtPhone_Number.setDisable(cond);
		txtEmail.setDisable(cond);
		txtArea_Notes.setDisable(cond);
		cmbStatus.setDisable(cond);
		btnHistory.setDisable(cond);
		btnLates_Lostbook.setDisable(cond);
		btnSave.setDisable(cond);
	}
	public void resetField() {
		txtFirst_Name.setText("");
		txtLast_Name.setText("");
		txtPhone_Number.setText("");
		txtEmail.setText("");
		txtArea_Notes.setText("");
		cmbStatus.setValue("");
	}
	@Override
	public void showFailed(String message) {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
		resetField();
		setFields(true);
	}
	private void setMsStatusComboBox() {
		ArrayList<String> msStatusList = new ArrayList<String>();	
		msStatusList.add("Locked");
		msStatusList.add("Frozen");
		msStatusList.add("Active");

		list = FXCollections.observableArrayList(msStatusList);
		cmbStatus.setItems(list);

	}

	@Override
	public void freshStart() {
		// TODO Auto-generated method stub
		
	}
	
}
