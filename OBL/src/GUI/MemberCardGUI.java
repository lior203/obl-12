package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import Common.GuiInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import logic.CommonController;
import logic.Main;
import logic.RegistrationController;

public class MemberCardGUI implements Initializable,GuiInterface{
	public static String memberIDHistory=null;
    String isManager;
	ObservableList<String> list;
	boolean update=false;
    @FXML
    private ComboBox cmbStatus;
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
    void searchMember(KeyEvent event) {
    	if (event.getCode()==KeyCode.ENTER) {
			RegistrationController.searchMember(txtMember_ID.getText());
		}
    }
    @FXML
    void librarianUpdateMember(ActionEvent event) {
    	update=true;
    	CommonController.librarianUpdateMember(cmbStatus.getValue().toString(),txtMember_ID.getText(),txtArea_Notes.getText(),isManager);
    }
    @FXML
    void viewPersonalHistory(ActionEvent event) throws IOException {
    	//Load page of loan history
    	memberIDHistory=txtMember_ID.getText();
    	Parent parent=FXMLLoader.load(getClass().getResource("/GUI/HistoryOfLoanTableView.fxml"));
    	Scene scene=new Scene(parent);
    	Stage stage=new Stage();
    	stage.setScene(scene);
//    	stage.setMaxHeight(578);
//    	stage.setMinHeight(578);
//    	stage.setMinWidth(950);
//    	stage.setMaxWidth(950);
    	stage.show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Client.clientUI=this;
		setMsStatusComboBox();
		CommonController.checkManager(Client.arrayUser.get(0));
	}

	@Override
	public void showSuccess(String string) {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(string);
		alert.showAndWait();
	}

	@Override
	public void display(Object obj) {
		ArrayList<String>userData=(ArrayList<String>) obj;
		if (userData.get(0).equals("CheckLibrarianManager")) {
			isManager=userData.get(1);
			setFields(true);
		}
		else if (userData.get(0).equals("SearchMember"))
		{
			System.out.println(userData);
			if (update==false) {
				setCardMember(userData);
			}
			if (userData.get(1).equals("NotExist")) {
				
				showFailed("Member does not exist");
			}
			else {
				if (update) {
					showSuccess("Member updated successfully");
				}
			}
			if (isManager.equals("true")) {
				setEditableLibrarianManager();
			}
			else {
				setEditableLibrarian();
			}
		}
	}
	private void setEditableLibrarian() {
		txtFirst_Name.setEditable(false);
		txtLast_Name.setEditable(false);
		txtPhone_Number.setEditable(false);
		txtEmail.setEditable(false);
		txtArea_Notes.setEditable(true);
		cmbStatus.setEditable(false);
		btnSave.setDisable(false);//librarian cannot edit details
		txtFirst_Name.setDisable(true);
		txtLast_Name.setDisable(true);
		txtPhone_Number.setDisable(true);
		txtEmail.setDisable(true);
		cmbStatus.setDisable(true);
		txtArea_Notes.setDisable(false);
		btnHistory.setDisable(false);
		btnLates_Lostbook.setDisable(false);
	}
	private void setEditableLibrarianManager() {
		txtFirst_Name.setEditable(false);
		txtLast_Name.setEditable(false);
		txtPhone_Number.setEditable(false);
		txtEmail.setEditable(false);
		txtArea_Notes.setEditable(true);
		cmbStatus.setEditable(false);
		txtArea_Notes.setDisable(false);
		btnSave.setDisable(false);//librarian cannot edit details
		cmbStatus.setDisable(false);
		btnHistory.setDisable(false);
		btnLates_Lostbook.setDisable(false);
	}
	private void setCardMember(ArrayList<String> memberData) {
		txtMember_ID.setEditable(false);
		txtFirst_Name.setText(memberData.get(5));
		txtLast_Name.setText(memberData.get(6));
		txtPhone_Number.setText(memberData.get(2));
		txtEmail.setText(memberData.get(3));
		cmbStatus.setValue(memberData.get(7));
		txtArea_Notes.setText(memberData.get(8));

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
		freshStart();
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
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
		txtFirst_Name.setText("");
		txtLast_Name.setText("");
		txtPhone_Number.setText("");
		txtEmail.setText("");
		txtArea_Notes.setText("");
		cmbStatus.setValue("");			
	}
	
}
