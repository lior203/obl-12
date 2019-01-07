package logic;

import java.io.IOException;
import java.util.ArrayList;

import Client.Client;
import GUI.InventoryAddGUI;
import GUI.InventoryRemoveGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InventoryController {

	public  static void AddBook(String bookname,String edition,String theme,String author,String printdate, String catalognumber, String copies, String purchasedate, String shelflocation, String wanted, String description)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("AddBook");
		inventoryData.add(catalognumber);
		inventoryData.add(bookname);
		inventoryData.add(copies);
		inventoryData.add(wanted);
		inventoryData.add(author);
		inventoryData.add(edition);
		inventoryData.add(printdate);
		inventoryData.add(theme);
		inventoryData.add(description);
		inventoryData.add(purchasedate);
		inventoryData.add(shelflocation);
		Main.client.handleMessageFromClientUI(inventoryData);
	}

	public  static void RemoveBook(String catalognumber)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("RemoveBook");
		inventoryData.add(catalognumber);
		Main.client.handleMessageFromClientUI(inventoryData);
	}

	public  static void SearchBook(String catalognumber)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("InventorySearchBook");
		inventoryData.add(catalognumber);
		Main.client.handleMessageFromClientUI(inventoryData);
	}	
}
