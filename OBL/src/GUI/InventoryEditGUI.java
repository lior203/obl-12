package GUI;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.sg.prism.NGEllipse;

import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import logic.InventoryController;
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
		freshStart();
		Disable(true);
		txtBook_ID.setDisable(false);
		txtBook_Name.setDisable(true);
		txtAuthors.setDisable(true);
	}

	@FXML
	void book_name(ActionEvent event) {
		freshStart();
		Disable(true);
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
			System.out.println("2");
			InventoryController.checkExistence(txtBook_Name.getText(), txtAuthors.getText());
		}
	}

	@FXML
	void Enter_BookID(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER){

		}
	}

	@FXML
	void Save(ActionEvent event) {

	}

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub

	}

	public void Disable(boolean choice) {
//		rdioBook_ID.setText(details.get(1));
		txtEdition.setDisable(choice);
		txtTheme.setDisable(choice);
		txtCopies.setDisable(choice);
		txtLocation.setDisable(choice);
		txtWanted.setDisable(choice);
		txtPrint_date.setDisable(choice);
		txtPurchase_Date.setDisable(choice);
		txtDescription.setDisable(choice);
	}
	
	@Override
	public void display(Object obj) {
		Disable(false);
		ArrayList<String> details=(ArrayList<String>)obj;
		txtBook_ID.setText(details.get(1));
		txtEdition.setText(details.get(6));
		txtTheme.setText(details.get(8));
		txtCopies.setText(details.get(3));
		txtCopies.setDisable(true);
		txtLocation.setText(details.get(12));
		txtWanted.setText(details.get(4));
		txtPrint_date.setPromptText(details.get(7));
		txtPurchase_Date.setPromptText(details.get(10));
		txtDescription.setText(details.get(9));
	}

	@Override
	public void showFailed(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	@Override
	public void freshStart() {
		txtBook_ID.clear();
		txtEdition.clear();
		txtTheme.clear();
		txtCopies.clear();
		txtLocation.clear();
		txtWanted.clear();
		txtPrint_date.setValue(null);
		txtPurchase_Date.setValue(null);
		txtDescription.clear();
		txtAuthors.clear();
		txtBook_Name.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.client.clientUI=this;		
	}

}

