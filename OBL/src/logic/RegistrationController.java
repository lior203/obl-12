package logic;

import java.io.IOException;
import java.util.ArrayList;
import Client.Client;
import GUI.OBLcontroller;

public class RegistrationController {

	static Client client;

	public  static void registration(String phoneNumber,String id,String lastName,String firstName,String Email, String password)
	{
		client= new Client(Main.host, Main.DEFAULT_PORT);
		System.out.println(phoneNumber);
		ArrayList<String> registrationData = new ArrayList<>();
		registrationData.add("Registration");
		registrationData.add(phoneNumber);
		registrationData.add(id);
		registrationData.add(lastName);
		registrationData.add(firstName);
		registrationData.add(Email);
		registrationData.add(password);
		try {
			client.sendToServer(registrationData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static void login(String id,String password)
	{
		client= new Client(Main.host, Main.DEFAULT_PORT);
		ArrayList<String> loginData = new ArrayList<>();
		loginData.add("Login");
		loginData.add(id);
		loginData.add(password);
		try {
			client.sendToServer(loginData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static void loginResult(ArrayList<String> arrayObject)
	{
			if(arrayObject.get(3).equals("1")) {
				OBLcontroller.openLibrarianMenuScreen();
			}
			else if(arrayObject.get(3).equals("2")) {
				OBLcontroller.openLibrarianMenuScreen();
			}
		
	}

}
