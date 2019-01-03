package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GUI.LibrarianMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OBLcontroller  {
	
 // i am lior
	    @FXML
	    private ImageView imag;

	    @FXML
	    private Pane pane;

	    @FXML
	    private Button btnLogin;

	    @FXML
	    private Button btnSearchBook;

	    @FXML
	    private Label lblForgot;

	    @FXML
	    private TextField txtUserName;

	    @FXML
	    private TextField txtPassword;
	    
	    public void start(Stage primaryStage) throws Exception {	
	    	Parent root = FXMLLoader.load(getClass().getResource("/GUI/OBL-openScreen.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("OBL System");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();				
		}

		
		    public void login(ActionEvent event) throws IOException {
			 ((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				SplitPane root = loader.load(getClass().getResource("/GUI/LibrarianMenu.fxml").openStream());
				//LibrarianMenuController studentFormController = loader.getController();
				Scene scene = new Scene(root);	
				primaryStage.setScene(scene);		
				primaryStage.show();
		    }
		    
		    public void forgot(MouseEvent  event) throws IOException {
				 ((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					SplitPane root = loader.load(getClass().getResource("/GUI/ReaderMenu.fxml").openStream());
					//LibrarianMenuController studentFormController = loader.getController();
					Scene scene = new Scene(root);	
					primaryStage.setScene(scene);		
					primaryStage.show();
			    }
}
