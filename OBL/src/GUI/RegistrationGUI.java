package GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.RegistrationController;

public class RegistrationGUI {
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
    	RegistrationController.registration(txtPhone_number.getText(),txtID.getText(),txtLast_name.getText(),txtFirst_name.getText(),txtEmail.getText(),txtPassword.getText());

    }

}
