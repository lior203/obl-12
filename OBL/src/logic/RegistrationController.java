package logic;

import java.util.ArrayList;

import Client.Client;

public class RegistrationController {

	Client client;

	public  static void registration(String phoneNumber,String id,String lastName,String firstName,String Email, String password)
	{
		client= new Client(, port)
				System.out.println(phoneNumber);
		ArrayList<String> registrationData = new ArrayList<>();
		registrationData.add("Registration");
		registrationData.add(phoneNumber);
		registrationData.add(id);
		registrationData.add(lastName);
		registrationData.add(firstName);
		registrationData.add(Email);
		registrationData.add(password);

	}

}
