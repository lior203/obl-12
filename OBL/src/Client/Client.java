package Client;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 


import ocsf.client.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

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
	public Client(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
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
		System.out.println("Message received: " + msg);
		ArrayList<String> arrayObject = (ArrayList<String>)msg; //casting msg-Object to arraylist
		switch (((arrayObject).get(1))) {
		case "Login":
			RegistrationController.loginResult(arrayObject);
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

//public Client(String host, int port, ClientGuiController clientUI) throws IOException {
// super(host, port); //Call the superclass constructor
//// this.clientUI = clientUI;
// openConnection();
//}


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
	if(message.equals("getInfo")) {
		try {
			sendToServer(message);
		}
		catch(IOException e) {
			//		    	clientUI.showAlert("Could not send message to server. Terminating client.");
			quit();
		}
	}

	////	  if(message instanceof Student) {
	//		  try {
	//		    	sendToServer(message);
	//		  }
	//		    catch(IOException e) {
	////		    	clientUI.showAlert("Could not send message to server. Terminating client.");
	//		    	quit();
	//		    }
	//	  }
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
