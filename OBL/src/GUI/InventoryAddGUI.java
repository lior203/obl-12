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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.InventoryController;
import logic.Main;

public class InventoryAddGUI implements GuiInterface,Initializable{
	static String Location;
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
    private TextField txtCatlog_Number;

	@FXML
	private Button btnAdd;
	
	@FXML
    private Button btnCopy;

	@FXML
    void AddCopy(ActionEvent event) {
		showLocationScreen();
		InventoryController.addCopy(Location, txtCatlog_Number.getText());
    }
	
	@FXML
	void AddBook(ActionEvent event) {
		if (checkfields())
			showFailed("Fill all the dields");
		else {
			InventoryController.addBook(txtBook_Name.getText(), txtEdition.getText(), txtTheme.getText(), txtAuthor.getText(), txtPrint_Date.getText(),txtCopies.getText(),txtPurchase_Date.getText(),txtShelf_Location.getText(),txtWanted.getText(),txtDescription.getText());
			Enablefields(false);
		}
	}

	public void Enablefields(boolean status) {
		txtEdition.setDisable(status);
		txtTheme.setDisable(status);
		txtPrint_Date.setDisable(status);
		txtPurchase_Date.setDisable(status);
		txtShelf_Location.setDisable(status);
		txtDescription.setDisable(status);
		txtTable_Of_Content.setDisable(status);
		txtWanted.setDisable(status);
		btnAdd.setDisable(status);
	}

	@FXML
	void BackToInventory(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		MainPane.getChildren().setAll(pane);
	}

	@FXML
	void CheckExistense(ActionEvent event) {
		InventoryController.checkExistence(txtBook_Name.getText(),txtAuthor.getText());
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

	public void showLocationScreen() {
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		try {
			root = loader.load(getClass().getResource("/GUI/EnterCopyLocation.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		this.txtShelf_Location.setText(msg.get(12));
		Enablefields(true);
		btnCopy.setDisable(false);
	}

	@Override
	public void showFailed(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Message");
		alert.setHeaderText(string);
		alert.showAndWait();
	}

	@Override
	public void freshStart() {
		this.txtCopies.setText("");
		this.txtWanted.setText("");
		this.txtEdition.setText("");
		this.txtPrint_Date.setText("");
		this.txtTheme.setText("");
		this.txtDescription.setText("");
		this.txtPurchase_Date.setText("");
		this.txtShelf_Location.setText("");
		Enablefields(false);
	}
}
