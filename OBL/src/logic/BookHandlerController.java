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
	
	public static void returnBook(String copyID, String status) {	
		ArrayList<String> memberData = new ArrayList<>();
		memberData.add("Return Book");
		memberData.add(copyID);
		memberData.add(status);
		Main.client.handleMessageFromClientUI(memberData);
	}
	
//	public static void isCopyLate(String copyID) {	
//		ArrayList<String> copyData = new ArrayList<>();
//		copyData.add("Check If Copy Is Late");
//		copyData.add(copyID);
//		Main.client.handleMessageFromClientUI(copyData);
//	}
	
	public static void isMemberLateOnReturn(String memberID) {	
		ArrayList<String> memberData = new ArrayList<>();
		memberData.add("Check If Member Is Late On Return");
		memberData.add(memberID);
		Main.client.handleMessageFromClientUI(memberData);
	}

	public static void reserveBook(String bookID, String memberID) {
		// TODO Auto-generated method stub
		
	}
	
	public static void isCopyWanted(String bookID) {	
		ArrayList<String> bookData = new ArrayList<>();
		bookData.add("Check Copy Wanted Status");
		bookData.add(bookID);
		Main.client.handleMessageFromClientUI(bookData);
	}
	
	public static void loanBook(String copyID,String bookStatus,String bookID,String memberID) {	
		ArrayList<String> copyData = new ArrayList<>();
		copyData.add("Loan Book");
		copyData.add(copyID);
		copyData.add(bookStatus);
		copyData.add(bookID);
		copyData.add(memberID);
		Main.client.handleMessageFromClientUI(copyData);
	}
}
