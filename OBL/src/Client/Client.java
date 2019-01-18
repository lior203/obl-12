package Client;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 


import ocsf.client.*;

import java.io.*;
import java.util.ArrayList;
import Common.Copy;
import Common.GuiInterface;
import Common.InventoryBook;
import GUI.InventoryAddGUI;
import GUI.InventoryRemoveGUI;
import GUI.OBLcontroller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import logic.InventoryController;
import logic.RegistrationController;


/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class Client extends AbstractClient
{
	public static GuiInterface clientUI;
	public static  ArrayList<String> arrayUser=new ArrayList<String>();


	public Client(String host, int port,GuiInterface clientUI) {
		super(host, port);
		this.clientUI=clientUI;
		try {
			openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Instance variables **********************************************

	/**
	 * The interface type variable.  It allows the implementation of 
	 * the display method in the client.
	 */
	//	ClientGuiController clientUI; 


	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) 
	{
		System.out.println("Message received: " + msg+" receive on the client side");
		ArrayList<String> arrayObject = (ArrayList<String>)msg; //casting msg-Object to arraylist
		switch ((((ArrayList<String>) msg).get(0))) {
		case "AddBook":
			Platform.runLater(()->{
				if (((ArrayList<String>) msg).get(arrayObject.size()-1).equals("1")) {
					clientUI.showSuccess("Book Added successfull. \n copy id is: "+((ArrayList<String>) msg).get(arrayObject.size()-3).toString());
					clientUI.freshStart();
				}
				else 
					clientUI.showFailed("Add failed");
			});
			break;
		case "RemoveCopy":
			Platform.runLater(()->{
				if (arrayObject.get(arrayObject.size()-1).equals("1"))
					clientUI.showSuccess("Copy Remove successfully");
				else
					clientUI.showFailed("remove failed.");
			});
			break;
		case "InventoryCheckExistense":
			if (((ArrayList<String>) msg).get(arrayObject.size()-1).equals("not exist")) {
				Platform.runLater(()->{
					clientUI.showFailed("book not exist. pls fill the missing details to add the book.");
					clientUI.freshStart();
				});
			}
			else
				Platform.runLater(()->{
					clientUI.display((ArrayList<String>) msg);
				});
			break;
		case "Login":
			arrayUser.add(((ArrayList<String>)msg).get(1));
			arrayUser.add(((ArrayList<String>)msg).get(2));
			arrayUser.add(((ArrayList<String>)msg).get(3));
			System.out.println((ArrayList<String>)msg);
			clientUI.display((ArrayList<String>) msg);
			break;
		case "SearchMember":
			if (((ArrayList<String>) msg).get(1).equals("NotExist")) {
				Platform.runLater(() -> {					
					clientUI.showFailed("Member does not exist in the system");
				});
			}
			else
			{
				Platform.runLater(() -> {					
					clientUI.display((ArrayList<String>) msg);
				});
			}
			break;
		case "Search book":
			if (((ArrayList<String>) msg).get(3).equals("-1"))
			{
				Platform.runLater(() -> {
					clientUI.showFailed("not found");
				});
			}
			else if (((ArrayList<String>) msg).get(3).equals("1"))
			{
				Platform.runLater(() -> {
					clientUI.display(msg);
				});
			}
			break;
		case "Check Member Existence":
			clientUI.display((ArrayList<String>)msg);
			break;
		case "Check Copy Loan Status":
			if(((ArrayList<String>)msg).size() == 1) {
				Platform.runLater(() -> {
					clientUI.showFailed("Copy isn't loan yet");
				});
			}
			else {
				clientUI.display((ArrayList<String>)msg);
			}
			break;
		case "Check Copy ID Existence":
			if(((ArrayList<String>)msg).size() == 1) {
				Platform.runLater(() -> {
					clientUI.showFailed("Copy doesn't exist");
				});
			}
			else {
				clientUI.display((ArrayList<String>)msg);
			}
			break;
		case "Return Book":
			if(((ArrayList<String>)msg).size() == 1) {
				Platform.runLater(() -> {
					clientUI.showFailed("Book return was unsuccessful!");
				});
			}
			else {
				clientUI.display((ArrayList<String>)msg);
			}
			break;
		case "Registration":
			System.out.println(msg);
			if(((ArrayList<String>)msg).get(7).equals("0"))
			{
				Platform.runLater(() -> {
					clientUI.showFailed("Some user have this ID");
				});
			}
			else  if (((ArrayList<String>)msg).get(7).equals("1"))
			{
				Platform.runLater(() -> {
					clientUI.showSuccess("The user have been added successfully");
				});
			}
			else if (((ArrayList<String>)msg).get(7).equals("2"))
			{
				Platform.runLater(() -> {
					clientUI.showSuccess("Some user have this phone number");
				});
			}
			break;
		case "AddCopy":
			if (((ArrayList<String>) msg).get(arrayObject.size()-1).equals("success")) {
				Platform.runLater(()->{
					clientUI.showSuccess("Copy Added successfuly.   copy id is: "+((ArrayList<String>) msg).get(arrayObject.size()-2).toString());
				});
			}else
				clientUI.showFailed("failed to add copy");
			break;
		case "checkExistenceByCopy":
			Platform.runLater(()->{
				if (((ArrayList<String>) msg).get(arrayObject.size()-1).equals("1"))
					clientUI.display(msg);
				else 
					clientUI.showFailed("copy not exist.");
			});
			break;
		case "SearchBookDetailes":
			Platform.runLater(()->{
				clientUI.display(msg);
			});
			break;

		default:
			break;
		}
	}
	//Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host The server to connect to.
	 * @param port The port number to connect on.
	 * @param clientUI The interface type variable.
	 */



	//Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */


	/**
	 * This method handles all data coming from the UI            
	 *
	 * @param message The message from the UI.    
	 */
	public void handleMessageFromClientUI(Object message)  
	{
		try {
			sendToServer(message);
		} catch(IOException e) {
			clientUI.showFailed("Could not send message to server. Terminating client.");
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}
}
//End of Client class
