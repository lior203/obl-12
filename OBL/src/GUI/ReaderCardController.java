package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReaderCardController {

    @FXML
    private TextField txtCardID;

    @FXML
    private TextField txtFirst_Name;

    @FXML
    private TextField txtLast_Name;

    @FXML
    private TextField txtPhone_Number;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtBirth_Date;

    @FXML
    private TextField txtAddress;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnLates_Lostbook;

    @FXML
    private TextArea txtArea_Notes;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cmbStatus;

}