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

public class InventoryGUI {
	 @FXML
	    private AnchorPane MainPane;
	 
	 @FXML
	    void AddScreen(ActionEvent event) throws IOException {
		 AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/InventoryAdd.fxml"));
		 MainPane.getChildren().setAll(pane);
	    }

	    @FXML
	    void EditScreen(ActionEvent event) throws IOException {
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/InventoryEdit.fxml"));
			 MainPane.getChildren().setAll(pane);
	    }

	    @FXML
	    void RemoveScreen(ActionEvent event) throws IOException {
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/InventoryRemove.fxml"));
			 MainPane.getChildren().setAll(pane);
	    }
	    
	   
}
