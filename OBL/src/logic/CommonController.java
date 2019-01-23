package logic;

import java.util.ArrayList;

import Common.Member;

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
	public static void checkManager(String ID) {
		ArrayList<String> librarianData = new ArrayList<>();
		librarianData.add("CheckLibrarianManager");
		librarianData.add(ID);
		Main.client.handleMessageFromClientUI(librarianData);
	}

	public static void librarianUpdateMember(String status, String ID, String notes, String isManager) {
		ArrayList<String> memberData = new ArrayList<>();
		memberData.add("librarianUpdateMember");
		memberData.add(ID);
		memberData.add(status);
		memberData.add(notes);
		memberData.add(isManager);
		Main.client.handleMessageFromClientUI(memberData);
	}
	
	/**
	 * get string of date and return arraylist of integer that contains the date as integer
	 * @param date-
	 * @return datearray[0]=year,datearray[1]=day,datearray[2]=month
	 */
	public static ArrayList<Integer> convertordate(String date) {     
		String year=(String) date.subSequence(0, 4);
		String day=(String) date.subSequence(8, 10);
		String month=(String) date.subSequence(5, 7);
		System.out.println("year-"+year +"day-"+day+"month-"+month);
		int year1,day2,month3;
		year1=Integer.parseInt(year);
		day2=Integer.parseInt(day);
		month3=Integer.parseInt(month);
		ArrayList<Integer> datearray=new ArrayList<>();
		datearray.add(year1);
		datearray.add(day2);
		datearray.add(month3);
		return datearray;
	}

	public static void viewPersonalHistory(String memberID) {
			ArrayList<String> memberData = new ArrayList<>();
			memberData.add("ViewPersonalHistory");
			memberData.add(memberID);
			System.out.println(memberID);
			Main.client.handleMessageFromClientUI(memberData);
	}
	
	public static String getCurrentTime() {
		java.util.Date date= new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(date);
		return currentTime;
	}
}
