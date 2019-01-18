package GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Client;
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

public class LibrarianMenuGUI implements Initializable{

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


	public void init() throws IOException {
		leftPane.maxWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
		leftPane.minWidthProperty().bind(mainSplitPane.widthProperty().multiply(0.1855));
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/Registration.fxml"));
		rightPane.getChildren().setAll(pane);
		Client.arrayUser.get(1);
		lblUser_name.setText(Client.arrayUser.get(1)+" "+Client.arrayUser.get(2));               
	}

	public void Logout(ActionEvent event) throws IOException {
		OBLcontroller.librarianStage.close();
		RegistrationController.logout(Client.arrayUser.get(0),Client.arrayUser.get(1));
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
		AnchorPane pane=FXMLLoader.load(getClass().getResource("/GUI/LibrarianSearch.fxml"));
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
