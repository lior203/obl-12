package logic;

import java.io.IOException;
import java.util.ArrayList;

import Client.Client;

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

}
