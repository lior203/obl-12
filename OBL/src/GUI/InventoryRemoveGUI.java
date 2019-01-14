package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.Client;
import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import logic.InventoryController;

public class InventoryRemoveGUI implements Initializable, GuiInterface {

	@FXML
	private AnchorPane MainPane;

	@FXML
	private Button btnRemove;

	@FXML
	private TextField txtBook_Name;

	@FXML
	private TextField txtEdition;

	@FXML
	private TextField txtTheme;

	@FXML
	private TextField txtAuthor;

	@FXML
	private TextField txtPrint_Date;

	@FXML
	private TextField txtCatalog_Number;

	@FXML
	private TextField txtCopies;

	@FXML
	private TextField txtPurchase_Date;

	@FXML
	private TextField txtShelf_Location;

	@FXML
	private TextArea txtDescription;

	@FXML
	private TextField txtWanted;


	@FXML
	void RemoveCopy(ActionEvent event) {
		InventoryController.RemoveCopy(txtCatalog_Number.getText());
	}

	@FXML
	void PressEnter(KeyEvent event) {
		if (event.getCode()==KeyCode.ENTER) {
			InventoryController.checkExistenceByCopy(txtCatalog_Number.getText());
		}
	}

	@FXML
	void BackToInventory(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		MainPane.getChildren().setAll(pane);
	}

	public void enable(boolean choice) {
		this.txtBook_Name.setDisable(choice);
		this.txtEdition.setDisable(choice);
		this.txtTheme.setDisable(choice);
		this.txtAuthor.setDisable(choice);
		this.txtPrint_Date.setDisable(choice);
		this.txtCopies.setDisable(choice);
		this.txtPurchase_Date.setDisable(choice);
		this.txtShelf_Location.setDisable(choice);
		this.txtDescription.setDisable(choice);
		this.txtWanted.setDisable(choice);
		this.btnRemove.setDisable(choice);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Client.clientUI=this;

	}


	@Override
	public void display(Object msg) {
		this.txtBook_Name.setText(((ArrayList<String>) msg).get(3));
		this.txtCopies.setText(((ArrayList<String>) msg).get(4));
		this.txtWanted.setText(((ArrayList<String>) msg).get(5));
		this.txtAuthor.setText(((ArrayList<String>) msg).get(6));
		this.txtEdition.setText(((ArrayList<String>) msg).get(7));
		this.txtPrint_Date.setText(((ArrayList<String>) msg).get(8));
		this.txtTheme.setText(((ArrayList<String>) msg).get(9));
		this.txtDescription.setText(((ArrayList<String>) msg).get(10));
		this.txtPurchase_Date.setText(((ArrayList<String>) msg).get(11));
		this.txtShelf_Location.setText(((ArrayList<String>) msg).get(1));
		btnRemove.setDisable(false);
	}

	@Override
	public void showFailed(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();	
		freshStart();
	}

	@Override
	public void showSuccess(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(message);
		alert.showAndWait();	
		freshStart();
	}

	@Override
	public void freshStart() {
		this.txtBook_Name.clear();
		this.txtCopies.clear();
		this.txtWanted.clear();
		this.txtAuthor.clear();
		this.txtEdition.clear();
		this.txtPrint_Date.clear();
		this.txtTheme.clear();
		this.txtDescription.clear();
		this.txtPurchase_Date.clear();
		this.txtShelf_Location.clear();
		btnRemove.setDisable(true);
	}
}

