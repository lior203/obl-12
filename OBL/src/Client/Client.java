package Client;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 


import ocsf.client.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.GuiInterface;
import Common.InventoryBook;
import GUI.InventoryRemoveGUI;
import GUI.OBLcontroller;
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
//			InventoryController.returnvalue(msg);
			break;
		case "RemoveBook":
			clientUI.showSuccess();
			break;
		case "InventorySearchBook":
			clientUI.display((ArrayList<String>) msg);
			break;
		case "Login":
			clientUI.display((ArrayList<String>) msg);
			break;
		case "Search book":
			if (((ArrayList<String>) msg).get(3).equals("-1"))
			{
				clientUI.showFaild("not found");
			}
			if (((ArrayList<String>) msg).get(3).equals("1"))
				clientUI.display(msg);
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
			  	clientUI.showFaild("Could not send message to server. Terminating client.");
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
