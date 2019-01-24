package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Common.GuiInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ActivitiesHistoryGUI implements Initializable,GuiInterface {

	@FXML
    private Button btnHistory;
	
	
	
	
	
	
	
	
	
	
	
	 @FXML
	    void viewPersonalHistory(ActionEvent event) throws IOException {
	    	//Load page of loan history
	    	Parent parent=FXMLLoader.load(getClass().getResource("/GUI/HistoryOfLoanTableView.fxml"));
	    	Scene scene=new Scene(parent);
	    	Stage stage=new Stage();
	    	stage.setScene(scene);
	    	stage.setMaxHeight(578);
	    	stage.setMinHeight(578);
	    	stage.setMinWidth(845);
	    	stage.setMaxWidth(845);
	    	stage.show();
	    	}
	    Common.Member member;// object of Member details











		@Override
		public void showSuccess(String string) {
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(string);
			alert.showAndWait();						
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
