package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.lang.model.type.PrimitiveType;

import Client.Client;
import Common.GuiInterface;
import GUI.LibrarianMenuGUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import logic.Main;
import logic.RegistrationController;

public class OBLcontroller implements Initializable, GuiInterface {

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


//	public void start(Stage primaryStage) throws Exception {
//		setStage(primaryStage);
//		Parent root = FXMLLoader.load(getClass().getResource("/GUI/OBL-openScreen.fxml"));
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("OBL System");
//		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
//		primaryStage.show();				
//	}


	public void login(ActionEvent event) throws IOException {
		RegistrationController.login(txtUserName.getText(),txtPassword.getText());
		
	}


	public void forgot(MouseEvent  event) throws IOException {
	}



	public void openMemberMenuScreen() throws IOException {
		Main.primary.hide();
//		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		SplitPane root = loader.load(getClass().getResource("/GUI/ReaderMenu.fxml").openStream());
		Scene scene = new Scene(root);			
//		scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void openLibrarianMenuScreen() throws IOException {
		Main.primary.hide();
//		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		SplitPane root = loader.load(getClass().getResource("/GUI/LibrarianMenu.fxml").openStream());
		Scene scene = new Scene(root);			
//		scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Client.clientUI=this;

	}


	@Override
	public void display(Object msg) {
		if(((ArrayList<String>)msg).get(3).equals("1")) {
			Platform.runLater(()->{
			    try {
					openLibrarianMenuScreen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		else if(((ArrayList<String>)msg).get(3).equals("2")) {
			Platform.runLater(()->{
			    try {
			    	openMemberMenuScreen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}


	@Override
	public void showSuccess(String message) {
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
}
