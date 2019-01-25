package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import Common.GuiInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class MemberExtendLoanPeriodGUI implements Initializable, GuiInterface {

    @FXML
    private AnchorPane root;

    @FXML
    private Label titelLabel;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> bookNameCol;

    @FXML
    private TableColumn<?, ?> AuthorsNameCol;

    @FXML
    private TableColumn<?, ?> startLoanDateCol;

    @FXML
    private TableColumn<?, ?> endLoanDateCol;

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFailed(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freshStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}