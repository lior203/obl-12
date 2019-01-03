package GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class InventoryController {
	// this is the one
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
