package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Common.GuiInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.SearchBookController;
import logic.Main;

public class SearchBookGUI implements Initializable, GuiInterface{

	@FXML
	private RadioButton radio_btn_book_name;

	@FXML
	private ToggleGroup choice;

	@FXML
	private TextField txtBook_Name;

	@FXML
	private RadioButton radio_btn_authors_name;

	@FXML
	private TextField txtAuthor_Name;

	@FXML
	private RadioButton radio_btn_book_theme;

	@FXML
	private TextField txtBook_Theme;

	@FXML
	private RadioButton radio_btn_free_text;

	@FXML
	private TextField txtFree_Text;

	@FXML
	private Button btnSearch;

	@FXML
	void onSearchClick(ActionEvent event) {
		String searchPick;
		if (choice.getSelectedToggle().equals(radio_btn_book_name))
		{
			searchPick = "Book Name";
			SearchBookController.searchBook(searchPick,txtBook_Name.getText());
		}
		else if (choice.getSelectedToggle().equals(radio_btn_authors_name))
		{
			searchPick = "Authors Name";
			SearchBookController.searchBook(searchPick,txtAuthor_Name.getText());
		}
		else if (choice.getSelectedToggle().equals(radio_btn_book_theme))
		{
			searchPick = "Book Theme";
			SearchBookController.searchBook(searchPick,txtBook_Theme.getText());
		}
		else if (choice.getSelectedToggle().equals(radio_btn_free_text))
		{
			searchPick = "Free text";
			SearchBookController.searchBook(searchPick, txtFree_Text.getText());	
		}
	}

	@FXML
	void openAndCloseFields(ActionEvent event) 
	{

		if (choice.getSelectedToggle().equals(radio_btn_book_name))
		{
			clearFields();
			txtBook_Name.setDisable(false);
			txtAuthor_Name.setDisable(true);
			txtBook_Theme.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_authors_name))
		{
			clearFields();
			txtAuthor_Name.setDisable(false);
			txtBook_Name.setDisable(true);
			txtBook_Theme.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_book_theme))
		{
			clearFields();
			txtBook_Theme.setDisable(false);
			txtAuthor_Name.setDisable(true);
			txtBook_Name.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_free_text))
		{
			clearFields();
			txtFree_Text.setDisable(false);
			txtBook_Theme.setDisable(true);
			txtAuthor_Name.setDisable(true);
			txtBook_Name.setDisable(true);
		}

	}
	
	public  void clearFields() {
		txtBook_Name.clear();
		txtAuthor_Name.clear();
		txtBook_Theme.clear();
		txtFree_Text.clear();
	}

	public void displayNotFound() 
	{
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		try {
			root = loader.load(getClass().getResource("/GUI/BookNotFound.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showSuccess(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Object obj) {
		ArrayList<String> datalist = (ArrayList<String>)obj;
		int numberOfBook = (datalist.size()-4)/2;
		Platform.runLater(() -> {
			int i=0,j=0;
			Stage primaryStage = new Stage();
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setMinHeight(390);
			scrollPane.setMinWidth(380);
			scrollPane.setMaxHeight(390);
			scrollPane.setMaxWidth(380);
			VBox vBox = new VBox();
			vBox.setMinHeight(380);
			vBox.setMinWidth(370);
			Label label = new Label("Search result");
			label.setFont(new Font(22));
			label.setPadding(new Insets(0, 0, 0, 110));
			vBox.getChildren().add(label);
			HBox hBox2 = new HBox();
			Label label3 = new Label("Author name");
			Label label2 = new Label("Book name");
			label2.setPadding(new Insets(0, 120, 0, 0));
			hBox2.getChildren().addAll(label2,label3);
			label2.setFont(new Font(18));
			label3.setFont(new Font(18));
			HBox box = new HBox(new Label(""));
			vBox.getChildren().addAll(hBox2,box);
			while(i<numberOfBook)
			{
				Label bookNameInResult = new Label(datalist.get(j+4));
				Label authorNsmeInResult= new Label(datalist.get(j+5));
				bookNameInResult.setFont(new Font(14));
				authorNsmeInResult.setFont(new Font(14));
				HBox hBox = new HBox();
				bookNameInResult.setPadding(new Insets(0, 200, 0, 0));
				hBox.getChildren().addAll(bookNameInResult,authorNsmeInResult);
				vBox.getChildren().add(hBox);
				i++;
				j+=2;
			}
			scrollPane.setContent(vBox);
			Scene scene = new Scene(scrollPane);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.showAndWait();
		
			
	//		ListResultGUI listResultGUI=new ListResultGUI();
	//		listResultGUI.display(obj);
		});

	}

	@Override
	public void showFailed(String message) {
		if (message.equals("not found"))
		{
			Platform.runLater(() -> {
				displayNotFound();
			});
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.client.clientUI = this;

	}

	@Override
	public void freshStart() {
		// TODO Auto-generated method stub
		
	}
}
