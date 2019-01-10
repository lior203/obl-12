package GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CopyLocationGUI {


	@FXML
	private TextField txtCopy_Location;

	@FXML
	void Confirm(ActionEvent event) {
		InventoryAddGUI.Location=txtCopy_Location.getText();
	}

}
