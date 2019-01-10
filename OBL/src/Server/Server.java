package Server;

import java.io.IOException;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 

import java.sql.*;
import java.util.ArrayList;

import Common.InventoryBook;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class Server extends AbstractServer 
{
	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public Server(int port) 
	{
		super(port);
	}

	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient (Object msg, ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client);
		ArrayList<String> arrayObject = (ArrayList<String>)msg; //casting msg-Object to arraylist
		switch (((ArrayList<String>)msg).get(0)) {
		case "Registration":
			try {
				DBController.getInstance().registretion((ArrayList<String>) msg);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "Login":
			try {
				int menu = DBController.getInstance().login((ArrayList<String>) msg);
				arrayObject.add(Integer.toString(menu));
				client.sendToClient(arrayObject);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;

		case "AddBook":
			try {
				int menu=DBController.getInstance().addBookToInventory((ArrayList<String>) msg);
				((ArrayList<String>)msg).add(Integer.toString(menu));
				client.sendToClient((ArrayList<String>)msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "RemoveCopy":
			try {
				int menu=DBController.getInstance().RemoveCopy((ArrayList<String>) msg);
				((ArrayList<String>) msg).add(Integer.toString(menu));
				client.sendToClient((ArrayList<String>)msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "InventoryCheckExistense":
			try {
				client.sendToClient(DBController.getInstance().inventoryCheckExistence((ArrayList<String>) msg));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error");
			}
			break;
		case "Check Member Existence":
			try {
				client.sendToClient(DBController.getInstance().isMemberExist((ArrayList<String>)msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "Check Copy Loan Status":
			try {
				client.sendToClient(DBController.getInstance().isCopyLoaned((ArrayList<String>)msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;


		case "Check Copy ID Existence":
			try {
				client.sendToClient(DBController.getInstance().isCopyExist((ArrayList<String>)msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "Search book":
			try {
				ArrayList<String> answer = DBController.getInstance().searchBook((ArrayList<String>) msg);
				client.sendToClient(answer);
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}


	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */



	public static void main(String[] args) 
	{
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}	
		Server sv = new Server(port);
		try 
		{
			sv.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}


	}
}

//My Methods

