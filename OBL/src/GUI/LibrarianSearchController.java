package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class LibrarianSearchController {

	    @FXML
	    private RadioButton rdioCopy_number;
	    
	    @FXML
	    private RadioButton rdioBook_Name;

	    @FXML
	    private ToggleGroup lior;

	    @FXML
	    private TextField txtCopy_Number;

	    @FXML
	    private TextField txtBook_Name;

	    @FXML
	    private TextField txtAuthor_name;

	    @FXML
	    private TextField txtBook_Theme;

	    @FXML
	    private TextField txtFree_Text;
	    
	    @FXML
	    private HBox HBOX1;
	    
	    @FXML
	    private HBox HBOX2;
	    
	    @FXML
	    private HBox HBOX3;
	    
	    @FXML
	    void ByCopy(ActionEvent event) {
	    	txtBook_Name.setDisable(true);
	    	txtAuthor_name.setDisable(true);
	    	txtBook_Theme.setDisable(true);
	    	txtFree_Text.setDisable(true);
	    	HBOX3.setDisable(true);
	    }
	    
	    @FXML
	    void ByOther(ActionEvent event) {
	    	txtCopy_Number.setDisable(true);
	    	HBOX3.setDisable(false);
	    }

	    @FXML
	    void SearchBook(ActionEvent event) {

	    }
}