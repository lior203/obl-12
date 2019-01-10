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

//	@FXML
//	void PressEnter(KeyEvent event) {
//		if (event.getCode()==KeyCode.ENTER) {
//			System.out.println("i press enter");
//			InventoryController.(txtCatalog_Number.getText());
//			enable();
//		}
//	}

	@FXML
	void BackToInventory(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		MainPane.getChildren().setAll(pane);
	}

	public void enable() {
		this.txtBook_Name.setDisable(false);
		this.txtEdition.setDisable(false);
		this.txtTheme.setDisable(false);
		this.txtAuthor.setDisable(false);
		this.txtPrint_Date.setDisable(false);
		this.txtCopies.setDisable(false);
		this.txtPurchase_Date.setDisable(false);
		this.txtShelf_Location.setDisable(false);
		this.txtDescription.setDisable(false);
		this.txtWanted.setDisable(false);
		this.btnRemove.setDisable(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Client.clientUI=this;
		
	}


	@Override
	public void display(Object msg) {
		this.txtBook_Name.setText(((ArrayList<String>) msg).get(2));
		this.txtCopies.setText(((ArrayList<String>) msg).get(3));
		this.txtWanted.setText(((ArrayList<String>) msg).get(4));
		this.txtAuthor.setText(((ArrayList<String>) msg).get(5));
		this.txtEdition.setText(((ArrayList<String>) msg).get(6));
		this.txtPrint_Date.setText(((ArrayList<String>) msg).get(7));
		this.txtTheme.setText(((ArrayList<String>) msg).get(8));
		this.txtDescription.setText(((ArrayList<String>) msg).get(9));
		this.txtPurchase_Date.setText(((ArrayList<String>) msg).get(10));
		this.txtShelf_Location.setText(((ArrayList<String>) msg).get(11));
		
	}

	@Override
	public void showFailed(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub
		
	}
}

