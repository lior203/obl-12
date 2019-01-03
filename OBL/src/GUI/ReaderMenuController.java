package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ReaderMenuController implements Initializable{
	
	 @FXML
	    private SplitPane mainSplitPane;

	    @FXML
	    private AnchorPane leftPane;

	    @FXML
	    private Label lblUser_name;

	    @FXML
	    private ImageView asd;

	    @FXML
	    private AnchorPane rightPane;
	    
	    public void init() throws IOException {
	    	leftPane.maxWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
	    	leftPane.minWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/ReaderPersonalData.fxml"));
	    	rightPane.getChildren().setAll(pane);
	    }
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
				try {
					init();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	    
	    public void Logout(ActionEvent event) throws IOException {
			 ((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root = loader.load(getClass().getResource("/GUI/OBL-openScreen.fxml").openStream());
				//LibrarianMenuController studentFormController = loader.getController();
				Scene scene = new Scene(root);	
				primaryStage.setScene(scene);		
				primaryStage.show();
		    }
	
	 @FXML
	    void HistoryActivitiesScreen(ActionEvent event) {

	    }

	    @FXML
	    void OrderBookScreen(ActionEvent event) throws IOException {
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Orderbook.fxml"));
	    	rightPane.getChildren().setAll(pane);
	    }

	    @FXML
	    void PersonalDataScreen(ActionEvent event) throws IOException {
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/ReaderPersonalData.fxml"));
	    	rightPane.getChildren().setAll(pane);
	    }

	    @FXML
	    void SearchScreen(ActionEvent event) throws IOException {
	    	AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/SearchBook.fxml"));
	    	rightPane.getChildren().setAll(pane);
	    }
	  
}
