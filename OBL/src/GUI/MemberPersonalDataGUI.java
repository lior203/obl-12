package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.Main;



public class MemberPersonalDataGUI implements Initializable,GuiInterface{
	 	@FXML
	    private TextField txtFirst_Name;

	    @FXML
	    private TextField txtLast_Name;

	    @FXML
	    private TextField txtID;

	    @FXML
	    private TextField txtPhone_Number;

	    @FXML
	    private TextField txtEmail;
	    @FXML
	    private Button btnHistory;
	    @FXML
	    private TextField txtStatus;
	    @FXML
	    private Button btnSave;
	    @FXML
	    void updateMemberDetails(ActionEvent event) {

	    }
	    @FXML
	    void viewPersonalHistory(ActionEvent event) {

	    }
		@Override
		public void showSuccess(String string) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void display(Object obj) {
			ArrayList<String>memberData=(ArrayList<String>) obj;
			setCardMember(memberData);
			setEditableMember();
			//setFields(false);			
		}

		@Override
		public void showFailed(String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			Main.client.clientUI=this;
			init();
		}
		private void init() {
			//here get the card reader data through the memberID
		}
		private void setEditableMember() {
			txtFirst_Name.setEditable(false);
			txtLast_Name.setEditable(false);
			txtPhone_Number.setEditable(true);
			txtEmail.setEditable(true);
			txtStatus.setEditable(false);
		}
		private void setCardMember(ArrayList<String> memberData) {
			txtFirst_Name.setText(memberData.get(5));
			txtLast_Name.setText(memberData.get(6));
			txtPhone_Number.setText(memberData.get(2));
			txtEmail.setText(memberData.get(3));
			txtStatus.setText(memberData.get(7));
		}
/*		private void setFields(Boolean cond) {
			txtFirst_Name.setDisable(cond);
			txtLast_Name.setDisable(cond);
			txtPhone_Number.setDisable(cond);
			txtEmail.setDisable(cond);
			//cmbStatus.setDisable(cond);
			btnHistory.setDisable(cond);
		//	btnLates_Lostbook.setDisable(cond);
			btnSave.setDisable(cond);
		}*/
		@Override
		public void freshStart() {
			// TODO Auto-generated method stub
			
		}
}
