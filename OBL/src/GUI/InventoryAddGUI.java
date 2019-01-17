package GUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import Common.GuiInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import logic.InventoryController;
import logic.Main;

public class InventoryAddGUI implements GuiInterface,Initializable{
	public static String Location;
	public static String bookname;
	public static String bookid;
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
	private DatePicker txtPrint_Date;

	@FXML
	private TextField txtCopies;

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
	private DatePicker txtPurchase_Date;

	@FXML
	private TextField txtCopy_Location;

	@FXML
	private Button btnCopy_Location_Confirm;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@FXML
	void AddCopy(ActionEvent event) {
		InventoryController.addCopy(Location,bookname,bookid);
	}

	@FXML
	void AddBook(ActionEvent event) {
		if (checkfields())
			showFailed("Fill all the dields");
		else {
			InventoryController.addBook(txtBook_Name.getText(), txtEdition.getText(), txtTheme.getText(), txtAuthor.getText(), txtPrint_Date.getValue().format(formatter).toString(),txtCopies.getText(),txtPurchase_Date.getValue().toString(),txtShelf_Location.getText(),txtWanted.getText(),txtDescription.getText());
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
		if (txtBook_Name.getText().isEmpty()||txtAuthor.getText().isEmpty()) {
			showFailed("fill the missing fields.");
			txtBook_Name.setEditable(true);
			txtAuthor.setEditable(true);
		}
		else{
			InventoryController.checkExistence(txtBook_Name.getText(),txtAuthor.getText());
			txtPrint_Date.setDisable(false);
			txtPurchase_Date.setDisable(false);
			btnCopy.setDisable(true);
		}
	}

	public boolean checkfields() {
		if ((txtEdition.getText()).equals(""))
			return true;
		if ((txtTheme.getText()).equals(""))
			return true;
		if (txtPrint_Date.getValue()==null)
			return true;
		if (txtPurchase_Date.getValue()==null)
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
	}

	@Override
	public void display(Object obj) {
		Platform.runLater(()->{
			ArrayList<String> msg = (ArrayList<String>)obj;
			//			String day=(String) msg.get(10).subSequence(8, 10);
			//			String year=(String) msg.get(10).subSequence(0, 4);//  option to print the date in the field like    dd mm yyyy
			this.txtCatlog_Number.setText(msg.get(1));
			bookid=msg.get(1);
			bookname=msg.get(2);
			Location=msg.get(12);
			this.txtCopies.setText(msg.get(3));
			this.txtWanted.setText(msg.get(4));
			this.txtEdition.setText(msg.get(6));
			this.txtPrint_Date.setPromptText(msg.get(7));
			this.txtTheme.setText(msg.get(8));
			this.txtDescription.setText(msg.get(9));
			this.txtPurchase_Date.setPromptText(msg.get(10));
			this.txtShelf_Location.setText(msg.get(12));
			Enablefields(true);
			txtBook_Name.setDisable(true);
			txtAuthor.setDisable(true);
			btnCopy.setDisable(false);
			btnAdd.setDisable(true);
		});
	}

	@Override
	public void showFailed(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Message");
		alert.setHeaderText(string);
		alert.showAndWait();
		txtBook_Name.setEditable(false);
		txtAuthor.setEditable(false);
	}

	@Override
	public void freshStart() {
		this.txtCopies.clear();
		this.txtWanted.clear();
		this.txtEdition.clear();
		this.txtPrint_Date.setValue(null);
		this.txtTheme.clear();
		this.txtDescription.clear();
		this.txtPurchase_Date.setValue(null);//purchasedate.fromString(msg.get(10)));
		this.txtShelf_Location.clear();
		Enablefields(false);
		txtCatlog_Number.clear();		
	}
}
