package logic;

import java.util.ArrayList;

public class BookHandlerController {

	public static void isCopyLoaned(String copyID) throws Exception {	
		ArrayList<String> copyData = new ArrayList<>();
		copyData.add("Check Copy Loan Status");
		copyData.add(copyID);
		Main.client.handleMessageFromClientUI(copyData);
	}
	
	public static void isCopyExist(String copyID) throws Exception {	
		if(copyID.length() == 0) {
			throw new Exception("Copy ID field can't be empty");
		}	
		ArrayList<String> copyData = new ArrayList<>();
		copyData.add("Check Copy ID Existence");
		copyData.add(copyID);
		Main.client.handleMessageFromClientUI(copyData);
	}
	
	public static void returnBook(String copyID) {	
		ArrayList<String> copyData = new ArrayList<>();
		copyData.add("Return Book");
		copyData.add(copyID);
		Main.client.handleMessageFromClientUI(copyData);
	}

	public static void reserveBook(String bookID, String memberID) {
		// TODO Auto-generated method stub
		
	}
}
