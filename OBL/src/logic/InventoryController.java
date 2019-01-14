package logic;

import java.io.IOException;
import java.util.ArrayList;

import Client.Client;
import GUI.InventoryAddGUI;
import GUI.InventoryRemoveGUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InventoryController {

	public  static void addBook(String bookname,String edition,String theme,String author,String printdate, String copies, String purchasedate, String shelflocation, String wanted, String description)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("AddBook");
		inventoryData.add(bookname);
		inventoryData.add(shelflocation);
		inventoryData.add(wanted);
		inventoryData.add(author);
		inventoryData.add(edition);
		inventoryData.add(printdate);
		inventoryData.add(theme);
		inventoryData.add(description);
		inventoryData.add(purchasedate);
		Main.client.handleMessageFromClientUI(inventoryData);
	}

	public static void addCopy(String copylocation,String bookname,String bookid) 
	{
			ArrayList<String> inventoryData = new ArrayList<>();
			inventoryData.add("AddCopy");
			inventoryData.add(bookname);
			inventoryData.add(copylocation);
			inventoryData.add(bookid);
			Main.client.handleMessageFromClientUI(inventoryData);
	}

	public  static void RemoveCopy(String catalognumber)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("RemoveCopy");
		inventoryData.add(catalognumber);
		Main.client.handleMessageFromClientUI(inventoryData);
	}

	public  static void checkExistence(String bookname, String authorname)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("InventoryCheckExistense");
		inventoryData.add(bookname);
		inventoryData.add(authorname);
		Main.client.handleMessageFromClientUI(inventoryData);
	}	
	
	public  static void checkExistenceByCopy(String bookid)
	{
		ArrayList<String> inventoryData = new ArrayList<>();
		inventoryData.add("checkExistenceByCopy");
		inventoryData.add(bookid);
		Main.client.handleMessageFromClientUI(inventoryData);
	}	
}
