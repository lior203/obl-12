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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import logic.InventoryController;
import logic.Main;

public class InventoryAddGUI implements GuiInterface,Initializable{
	@FXML
	private AnchorPane MainPane;

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
	private TextField txtCopies;

	@FXML
	private TextField txtPurchase_Date;

	@FXML
	private TextField txtShelf_Location;

	@FXML
	private TextArea txtDescription;

	@FXML
	private TextField txtTable_Of_Content;

	@FXML
	private TextField txtWanted;

	@FXML
	private Button btnAdd;

	@FXML
	void AddBook(ActionEvent event) {
		if (checkfields())
			showFailed("Fill all the dields");
		else {
			System.out.println("GUI");
			InventoryController.addBook(txtBook_Name.getText(), txtEdition.getText(), txtTheme.getText(), txtAuthor.getText(), txtPrint_Date.getText(),txtCopies.getText(),txtPurchase_Date.getText(),txtShelf_Location.getText(),txtWanted.getText(),txtDescription.getText());
		}
	}

	public void Enablefields() {
		txtEdition.setDisable(false);
		txtTheme.setDisable(false);
		txtPrint_Date.setDisable(false);
		txtPurchase_Date.setDisable(false);
		txtShelf_Location.setDisable(false);
		txtDescription.setDisable(false);
		txtTable_Of_Content.setDisable(false);
		txtWanted.setDisable(false);
		btnAdd.setDisable(false);
	}

	@FXML
	void BackToInventory(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		MainPane.getChildren().setAll(pane);
	}

	@FXML
	void CheckExistense(ActionEvent event) {
		InventoryController.checkExistence(txtBook_Name.getText(),txtAuthor.getText());
		Enablefields();
	}

	public boolean checkfields() {
		if ((txtEdition.getText()).equals(""))
			return true;
		if ((txtTheme.getText()).equals(""))
			return true;
		if ((txtPrint_Date.getText()).equals(""))
			return true;
		if ((txtPurchase_Date.getText()).equals(""))
			return true;
		if ((txtShelf_Location.getText()).equals(""))
			return true;
		if ((txtDescription.getText()).equals(""))
			return true;
		if ((txtWanted.getText()).equals(""))
			return true;
		if ((btnAdd.getText()).equals(""))
			return true;
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.client.clientUI=this;		
	}

	@Override
	public void showSuccess(String string) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText(string);
		alert.showAndWait();	
		btnAdd.setDisable(true);
	}

	@Override
	public void display(Object obj) {
		ArrayList<String> msg = (ArrayList<String>)obj;
		this.txtCopies.setText(msg.get(3));
		this.txtWanted.setText(msg.get(4));
		this.txtEdition.setText(msg.get(6));
		this.txtPrint_Date.setText(msg.get(7));
		this.txtTheme.setText(msg.get(8));
		this.txtDescription.setText(msg.get(9));
		this.txtPurchase_Date.setText(msg.get(10));
	}

	@Override
	public void showFailed(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Message");
		alert.setHeaderText(string);
		alert.showAndWait();		
	}
}
