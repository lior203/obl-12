package GUI;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import logic.InventoryController;

public class InventoryAddGUI {
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
    private TextField txtCatlog_Number;

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
    void AddBook(ActionEvent event) {
    	InventoryController.AddBook(txtBook_Name.getText(), txtEdition.getText(), txtTheme.getText(), txtAuthor.getText(), txtPrint_Date.getText(), txtCatlog_Number.getText(),txtCopies.getText(),txtPurchase_Date.getText(),txtShelf_Location.getText(),txtWanted.getText(),txtDescription.getText());
    }
    
    @FXML
    void BackToInventory(ActionEvent event) throws IOException {
    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
    	MainPane.getChildren().setAll(pane);
    }
    public static void AddResult(Object msg) {
		ArrayList<String> arrayObject = (ArrayList<String>)msg;
		if (arrayObject.get(arrayObject.size()-1).equals("1")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setHeaderText("An error occurred");
			alert.setContentText("lior");
			alert.showAndWait();
		}
    }
}
