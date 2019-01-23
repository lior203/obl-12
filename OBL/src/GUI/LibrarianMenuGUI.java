package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Client;
import Common.GuiInterface;
import Common.Librarian;
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
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Main;
import logic.RegistrationController;

public class LibrarianMenuGUI implements Initializable,GuiInterface{

	@FXML
	private SplitPane mainSplitPane;

	@FXML
	private AnchorPane leftPane;

	@FXML
	private AnchorPane rightPane;

	@FXML
	private Button btnRegistration;

	@FXML
	private Button btnSearchReader;

	@FXML
	private Button btnSearchBook;

	@FXML
	private Button btnReturnBook;

	@FXML
	private Button btnInventory;

	@FXML
	private Button btnLog_out;

	@FXML
	private Button btnShow_Report;

	@FXML
	private Label lblUser_name;

	@FXML
	private ImageView asd;

	//	public void Display() throws IOException {
	//		Stage primaryStage=new Stage();
	//		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ReaderMenu.fxml"));
	//		Scene scene = new Scene(root);
	//		primaryStage.setTitle("Member Menu");
	//		primaryStage.setScene(scene);
	//		primaryStage.setResizable(false);
	//		primaryStage.show();				
	//	}

	public void init() throws IOException {
	Main.client.clientUI= this;
		leftPane.maxWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
		leftPane.minWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Registration.fxml"));
		rightPane.getChildren().setAll(pane);
		lblUser_name.setText(Client.arrayUser.get(2)+" "+Client.arrayUser.get(3)); 		
	}

	public void Logout(ActionEvent event) throws IOException {
		OBLcontroller.librarianStage.close();
		//System.out.println(Client.arrayUser.get(0)+" "+Client.arrayUser.get(1)+"inside LibrarianMenu");
		RegistrationController.logout(Client.arrayUser.get(0),Client.arrayUser.get(1));
		//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		//OBLcontroller.LibrarianStage.close();
		Client.arrayUser.clear();
		Main.primary.show();

	}

	@FXML
	void InventoryScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Inventory.fxml"));
		rightPane.getChildren().setAll(pane);
	}

	@FXML
	void RegistrationScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Registration.fxml"));
		rightPane.getChildren().setAll(pane);
	}

	@FXML
	void ReturnBookScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/ReturnBook.fxml"));
		rightPane.getChildren().setAll(pane);
	}

	@FXML
	void SearchBookScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/LibrarianBookSearch.fxml"));
		rightPane.getChildren().setAll(pane);
	}

	@FXML
	void SearchReaderScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/ReaderCard.fxml"));
		rightPane.getChildren().setAll(pane);
	}

	@FXML
	void ShowReportScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/ReaderCard.fxml"));
		rightPane.getChildren().setAll(pane);
	}
	
	@FXML
	void showLoanScreen(ActionEvent event) throws IOException {
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Loan.fxml"));
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

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Object obj) {
		OBLcontroller.librarianStage.close();
		Main.primary.show();
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
