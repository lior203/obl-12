package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.net.ssl.SSLException;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

	public static Stage LibrarianStage;
	public static Stage MemberStage;


	public void login(ActionEvent event) throws IOException {
		Main.client.clientUI=this;
		if(!(txtUserName.getText().isEmpty()==false&&txtPassword.getText().isEmpty()==false))
			showFailed("Some fields are empty");
		else {
			RegistrationController.login(txtUserName.getText(),txtPassword.getText());
			freshStart();
		}

	}


	public void forgot(MouseEvent  event) throws IOException {
	}



	public void openMemberMenuScreen() throws IOException {
		Main.primary.hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		SplitPane root = loader.load(getClass().getResource("/GUI/ReaderMenu.fxml").openStream());
		Scene scene = new Scene(root);			
		//		scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
		primaryStage.setScene(scene);	
		//Logout when pressed the "exit" button
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				RegistrationController.logout(Client.arrayUser.get(0),Client.arrayUser.get(1));
				System.out.println("Stage is closing");
			}
		});
		MemberStage=primaryStage;
		primaryStage.show();		
	}

	public void openLibrarianMenuScreen() throws IOException {
		Main.primary.hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		SplitPane root = loader.load(getClass().getResource("/GUI/LibrarianMenu.fxml").openStream());
		Scene scene = new Scene(root);			
		//		scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
		primaryStage.setScene(scene);
		//Logout when pressed the "exit" button
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				RegistrationController.logout(Client.arrayUser.get(0),Client.arrayUser.get(1));
				System.out.println("Stage is closing");
			}
		});
		primaryStage.setScene(scene);	
		LibrarianStage=primaryStage;
		primaryStage.show();		
	}

	public void searchBook(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/GUI/SearchBook.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);		
		primaryStage.show();
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.client.clientUI=this;
		freshStart();
	}


	@Override
	public void display(Object obj) {
		ArrayList<String> msg = (ArrayList<String>)obj;
		switch (msg.get(5)) {
		case "0":
			showFailed("The user is already logged into the system!");
			break;
		case "1":
			try {
				openLibrarianMenuScreen();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "2":
			try {
				openMemberMenuScreen();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "3":
			showFailed("The member already graduated hence he can't login!");
			break;
		}

//		//System.out.println(msg.toString()+"inside OBLcontroller");
//		if(((ArrayList<String>)msg).get(5).equals("1")) {//Check if the user is a librarian (1)
//			Platform.runLater(()->{
//				try {
//					openLibrarianMenuScreen();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});
//		}
//		else if(((ArrayList<String>)msg).get(5).equals("2")) {//Check if the user is a member (2)
//			Platform.runLater(()->{
//				try {
//					openMemberMenuScreen();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});
//		}
	}


	@Override
	public void showSuccess(String message) {
		// TODO Auto-generated method stub

	}


	@Override
	public void showFailed(String message) {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
		freshStart();
		//		setFields(true);

	}


	@Override
	public void freshStart() {
		txtUserName.clear();
		txtPassword.clear();
	}
}
