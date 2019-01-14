package GUI;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import logic.Main;

public class InventoryEditGUI implements Initializable,GuiInterface {

	@FXML
	private AnchorPane MainPane;

	@FXML
	private Button btnSave;

	@FXML
	private TextField txtBook_Name;

	@FXML
	private TextField txtEdition;

	@FXML
	private TextField txtTheme;

	@FXML
	private TextField txtAuthors;

	@FXML
	private TextField txtCatalog_Number;

	@FXML
	private TextField txtCopies;

	@FXML
	private TextField txtLocation;

	@FXML
	private TextArea txtDescription;

	@FXML
	private TextField txtWanted;

	@FXML
	private DatePicker txtPurchase_Date;

	@FXML
	private DatePicker txtPrint_date;

	@FXML
	private RadioButton rdioBook_ID;

	@FXML
	private ToggleGroup choice;

	@FXML
	private RadioButton rdioBook_Name;

	@FXML
	private TextField txtBook_ID;

	@FXML
	void BackToInventory(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		MainPane.getChildren().setAll(pane);
	}

	@FXML
	void book_ID(ActionEvent event) {
		txtBook_ID.setDisable(false);
		txtBook_Name.setDisable(true);
		txtAuthors.setDisable(true);
	}

	@FXML
	void book_name(ActionEvent event) {
		txtBook_ID.setDisable(true);
		txtBook_Name.setDisable(false);
		txtAuthors.setDisable(false);
	}

	@FXML
	void EnterBook_Name(KeyEvent event) {
		 if (event.getCode() == KeyCode.ENTER){
			 if (txtBook_Name.getText().isEmpty()||txtAuthors.getText().isEmpty()) {
			showFailed("fill book");	
			}
			 System.out.println("lior");
		 }
	}

	@FXML
	void Enter_BookID(KeyEvent event) {

	}

	@FXML
	void Save(ActionEvent event) {

	}

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showFailed(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void freshStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.client.clientUI=this;		
	}

}

