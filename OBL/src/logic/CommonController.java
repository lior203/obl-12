package logic;

import java.util.ArrayList;

public class CommonController {
	
	private static final int ID_SIZE = 9;

	
	public static void checkMemberExistence(String memberID) throws Exception {
		if(memberID.length() == 0) {
			throw new Exception("Member ID field can't be empty");
		}	
		
		if(memberID.length() != ID_SIZE) {
			throw new Exception("Wrong ID size");
		}
		
		ArrayList<String> checkMemberExistence = new ArrayList<>();
		checkMemberExistence.add("Check Member Existence");
		checkMemberExistence.add(memberID);
		Main.client.handleMessageFromClientUI(checkMemberExistence);
	}
	
	public static void changeMemberStatus(String memberID, String status) {
		ArrayList<String> memberData = new ArrayList<>();
		memberData.add("Change Member Status");
		memberData.add(memberID);
		memberData.add(status);
		Main.client.handleMessageFromClientUI(memberData);
	}
}
