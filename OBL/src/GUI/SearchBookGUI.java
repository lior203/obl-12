package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.Client;
import Common.GuiInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.SearchBookController;
import logic.BookHandlerController;
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
	private Button btnBack;

	@FXML
	void onBackClick(ActionEvent event) throws IOException {
		OBLcontroller.searchForReader.close();
		Main.primary.show();
	}

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
			freshStart();
			txtBook_Name.setDisable(false);
			txtAuthor_Name.setDisable(true);
			txtBook_Theme.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_authors_name))
		{
			freshStart();
			txtAuthor_Name.setDisable(false);
			txtBook_Name.setDisable(true);
			txtBook_Theme.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_book_theme))
		{
			freshStart();
			txtBook_Theme.setDisable(false);
			txtAuthor_Name.setDisable(true);
			txtBook_Name.setDisable(true);
			txtFree_Text.setDisable(true);
		}

		if(choice.getSelectedToggle().equals(radio_btn_free_text))
		{
			freshStart();
			txtFree_Text.setDisable(false);
			txtBook_Theme.setDisable(true);
			txtAuthor_Name.setDisable(true);
			txtBook_Name.setDisable(true);
		}

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
		if (((ArrayList<String>)obj).get(0).equals("SearchBookDetailes"))
			displayBookDetails((ArrayList<String>)obj);
		else {

			ArrayList<String>   		 datalist 		 = 	(ArrayList<String>)obj;
			int 						 numberOfBook    = 	(datalist.size()-4)/2;
			int 			 			 i				 =	0;
			int							 j				 =  0;
			VBox 						 vBox 		  	 = 	new VBox(20);
			HBox 						 hBox2			 =	new HBox();
			Label	   					 label			 =	new Label("Search result");
			Label 				  		 authorNameLabel = 	new Label("Author name");
			Label 						 bookNameLabel   = 	new Label("Book name");
			Stage 	   					 primaryStage    = 	new Stage();
			ScrollPane 				 	 scrollPane      = 	new ScrollPane();


			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setTitle("Search result");
			scrollPane.setMinHeight(390);
			scrollPane.setMinWidth(480);
			scrollPane.setMaxHeight(390);
			scrollPane.setMaxWidth(480);
			vBox.setMinHeight(380);
			vBox.setMinWidth(370);
			label.setFont(new Font(22));
			label.setPadding(new Insets(0, 0, 0, 170));
			vBox.getChildren().add(label);


			bookNameLabel.setPadding(new Insets(0, 120, 0, 0));
			hBox2.getChildren().addAll(bookNameLabel ,authorNameLabel);
			hBox2.setPadding(new Insets(0, 0, 0, 20));
			bookNameLabel.setFont(new Font(18));
			authorNameLabel.setFont(new Font(18));
			vBox.getChildren().add(hBox2);
			while(i<numberOfBook)
			{
				Label bookNameInResult = new Label(datalist.get(j+4));
				Label authorNsmeInResult= new Label(datalist.get(j+5));
				authorNsmeInResult.setMinWidth(115);
				bookNameInResult.setFont(new Font(14));
				authorNsmeInResult.setFont(new Font(14));
				HBox hBox = new HBox();
				bookNameInResult.setPadding(new Insets(0, 200, 0, 0));
				if (Client.arrayUser.size() <= 2)
				{
					hBox.getChildren().addAll(bookNameInResult,authorNsmeInResult);
					hBox.setPadding(new Insets(0, 0, 0, 20));
				}
				else {
					Button details = new Button("Check details");
					details.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							SearchBookController.searchBookDetailes(bookNameInResult.getText(),authorNsmeInResult.getText());		
						}
					});
					details.setMaxSize(90, 30);
					details.setMinSize(90, 30);
					details.setFont(new Font(12));
					hBox.getChildren().addAll(bookNameInResult,authorNsmeInResult,details);
					hBox.setPadding(new Insets(0, 60, 0, 20));
				}
				vBox.getChildren().add(hBox);
				i++;
				j+=2;
			}
			scrollPane.setContent(vBox);
			Scene scene = new Scene(scrollPane);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.showAndWait();
		}

	}

	private void displayBookDetails(ArrayList<String> detailesData) {

		Stage 	   	 primaryStage   = new Stage();
		VBox 	 	 mainVbox       = new VBox(20);
		Label 		 ans  			= new Label();
		Label		 detailes		= new Label();
		Scene 		 scene 			= new Scene(mainVbox);

		primaryStage.initModality(Modality.APPLICATION_MODAL);
		mainVbox.setMinHeight(390);
		mainVbox.setMinWidth(550);
		mainVbox.setMaxHeight(390);
		mainVbox.setMaxWidth(550);
		detailes.setText("Detailes result");
		detailes.setFont(new Font("Ariel", 22));
		mainVbox.getChildren().add(detailes);
		mainVbox.setAlignment(Pos.CENTER);
		primaryStage.setTitle("Detailes result");
		
		System.out.println(detailesData);

		if (detailesData.get(3).equals("1")) //return the location
		{
			ans.setText("The book " + detailesData.get(1) + " of the author " + detailesData.get(2) + " is in shelf " + detailesData.get(4));
			ans.setFont(new Font("Ariel", 16));
			mainVbox.getChildren().add(ans);
		}
		else {
			Button reserveBtn = new Button("Rrserve");
			reserveBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					BookHandlerController.reserveBook(detailesData.get(6),detailesData.get(5));
				}
			});
			Label  ans2		  = new Label();
			ans.setText("we don't have copy of " + detailesData.get(1) + " by the author " + detailesData.get(2) + " in the library.");
			ans2.setText(" the nearest return date is in " + detailesData.get(4));
			ans.setFont(new Font("Ariel", 16));
			ans2.setFont(new Font("Ariel", 16));
			ans.setPadding(new Insets(0, 0, 0, 20));
			ans2.setPadding(new Insets(0, 0, 0, 20));
			mainVbox.getChildren().addAll(ans,ans2,reserveBtn);
		}
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.showAndWait();

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
		txtBook_Name.clear();
		txtAuthor_Name.clear();
		txtBook_Theme.clear();
		txtFree_Text.clear();
	}
}
