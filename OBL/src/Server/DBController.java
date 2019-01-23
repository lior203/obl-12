package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.UpdatableResultSet;
import Client.Client;
import Common.Book;
import Common.InventoryBook;
import GUI.LibrarianMenuGUI;
import logic.CommonController;
import sun.awt.windows.ThemeReader;




public class DBController {

	private static DBController dbController = null;
	public static Connection conn;
	public static int bookGenerate;
	public static int copyGenerate;


	private DBController(Connection conn)
	{
		this.conn = conn;	
	}

	public static DBController getInstance()
	{
		if (dbController == null)
			dbController = new DBController(connectToDatabase());
		return dbController;
	}

	// register new user in database
	public static int registretion(ArrayList<String> data) throws SQLException
	{
		PreparedStatement preparedRegistretion;
		ResultSet rsRegistretion;

		preparedRegistretion = conn.prepareStatement("SELECT  * FROM members WHERE MemberID=? ");
		preparedRegistretion.setString(1, data.get(2));
		rsRegistretion = preparedRegistretion.executeQuery();
		if ((rsRegistretion.isBeforeFirst()))
		{
			return 0;
		}

		preparedRegistretion = conn.prepareStatement("SELECT  * FROM members WHERE PhoneNumber=? ");
		preparedRegistretion.setString(1, data.get(1));
		rsRegistretion = preparedRegistretion.executeQuery();
		if ((rsRegistretion.isBeforeFirst()))
		{
			return 0;
		}

		preparedRegistretion = conn.prepareStatement("SELECT  * FROM librarian WHERE LibrarianID=? ");
		preparedRegistretion.setString(1, data.get(2));
		rsRegistretion = preparedRegistretion.executeQuery();
		if ((rsRegistretion.isBeforeFirst()))
		{
			return 0;
		}

		PreparedStatement insert = conn.prepareStatement("insert into members values(?,?,?,?,?,?,?,?,?,?)");
		insert.setString(1, data.get(2));
		insert.setString(2, data.get(1));
		insert.setString(3, data.get(5));
		insert.setString(4, data.get(6));
		insert.setString(5, data.get(4));
		insert.setString(6, data.get(3));
		insert.setString(7, "Active");
		insert.setString(8, "empty");
		insert.setString(9, "0");
		insert.setString(10, "false");
		insert.executeUpdate();
		return 1;
	}

	//add book to inventory database
	public static int addBookToInventory(ArrayList<String> data) throws SQLException{
		int Result,maxBookID=0;
		String string = "SELECT MAX(BookID) FROM book";
		PreparedStatement getMaxID = conn.prepareStatement(string);
		ResultSet rs=getMaxID.executeQuery();
		if(rs.next()) {
			if (rs.getString(1)!=null) {
				maxBookID=Integer.parseInt(rs.getString(1));
			}
		}
		PreparedStatement insert = conn.prepareStatement("insert into book values(?,?,?,?,?,?,?,?,?,?,?,?)");
		insert.setString(1, Integer.toString(++maxBookID));// book id
		insert.setString(2, data.get(1));//book name
		insert.setString(3, "0");//copies
		insert.setString(4, data.get(3));//wanted
		insert.setString(5, data.get(4));//author name
		insert.setString(6, data.get(5));//edition
		insert.setString(7, data.get(6));//print date
		insert.setString(8, data.get(7));//book genre
		insert.setString(9, data.get(8));//description
		insert.setString(10, data.get(9));//purchase date
		insert.setString(11, "pdf");//pdf
		insert.setString(12, data.get(2));//shelf location
		Result=insert.executeUpdate();
		data.add(Integer.toString(maxBookID));
		addCopyToInventory(data);
		return Result;
	}

	public static ArrayList<String> addCopyToInventory(ArrayList<String> data) throws SQLException{
		int maxCopyID=0;
		String copyid,bookid;
		bookid=data.get(data.size()-1);
		PreparedStatement stmt = conn.prepareStatement("SELECT MAX(CopyID) FROM copies WHERE BookID = ?");
		stmt.setString(1, bookid);
		ResultSet rs = stmt.executeQuery();

		if(rs.next()) {
			if (rs.getString(1)!=null) {
				maxCopyID=Integer.parseInt(rs.getString(1).substring((rs.getString(1).indexOf("-"))+1));
			}
		}
		PreparedStatement insert = conn.prepareStatement("insert into copies values(?,?,?,?,?)");
		copyid=(data.get(data.size()-1)) + "-" + Integer.toString(++maxCopyID);
		insert.setString(1, copyid);
		insert.setString(2, data.get(1));
		insert.setString(3, "false");
		//		insert.setString(4, data.get(2));
		insert.setString(4, data.get(data.size()-1));
		insert.setString(5, null);
		insert.executeUpdate();
		data.add(copyid);
		data.add("success");
		updatecopyamount("increase",bookid);
		return data;
	}

	public static int updatecopyamount(String action,String bookid) throws SQLException {
		int amountcopies=0,answer;
		switch (action) {
		case "increase":
			PreparedStatement increse=conn.prepareStatement("UPDATE book SET copies=copies+1 WHERE bookID=?");
			increse.setString(1, bookid);
			increse.executeUpdate();
			break;
		case "decrease":
			PreparedStatement decrease=conn.prepareStatement("UPDATE book SET copies=copies-1 WHERE bookID=?");
			decrease.setString(1, bookid);
			decrease.executeUpdate();
			PreparedStatement stmt = conn.prepareStatement("SELECT copies FROM book WHERE BookID = ?");
			stmt.setString(1, bookid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				if (rs.getString(1).equals("0")) {
					PreparedStatement delete=conn.prepareStatement("DELETE FROM book WHERE bookID = ?");
					delete.setString(1, bookid);
					answer=delete.executeUpdate();
					return answer;
				}
			}
			break;
		default:
			break;
		}
		return 0;
	}


	public static int RemoveCopy(ArrayList<String> data) throws SQLException{
		String bookID;
		int answer=0;
		PreparedStatement getbookid = conn.prepareStatement("SELECT BookID FROM copies WHERE CopyID=?");
		getbookid.setString(1, data.get(1));
		ResultSet rs = getbookid.executeQuery();
		if(rs.next()) {
			bookID=rs.getString(1);
		}
		else return 0;
		String deleteSQL = "DELETE FROM copies WHERE CopyID = ?";
		PreparedStatement removestmt = conn.prepareStatement(deleteSQL);
		removestmt.setString(1, data.get(1));
		int res=removestmt.executeUpdate();
		if (res==1) {
			answer=updatecopyamount("decrease",bookID);
			if (answer==1) {
				res=2;
			}
		}
		return res;
	}

	public static ArrayList<String> checkExistenceByCopy(ArrayList<String> msg) throws SQLException {
		String bookid,location;
		ArrayList<String> result=new ArrayList<String>();
		result.add("checkExistenceByCopy");
		//		PreparedStatement getbook = conn.prepareStatement("SELECT BookID,ShelfLocation FROM copies WHERE CopyID=?");
		PreparedStatement getbook = conn.prepareStatement("SELECT BookID FROM copies WHERE CopyID=?");

		getbook.setString(1, msg.get(1));
		ResultSet rs = getbook.executeQuery();
		if(rs.next()) {
			bookid=(rs.getString(1));
			//			location=rs.getString(2);
		}
		else
			return result;
		PreparedStatement getbookdetails=conn.prepareStatement("SELECT * FROM book WHERE bookID=?");
		getbookdetails.setString(1, bookid);
		ResultSet rs1=getbookdetails.executeQuery();
		if(rs1.next()) {
			result.add(rs1.getString(1));
			result.add(rs1.getString(2));
			result.add(rs1.getString(3));
			result.add(rs1.getString(4));
			result.add(rs1.getString(5));
			result.add(rs1.getString(6));
			result.add(rs1.getString(7));
			result.add(rs1.getString(8));
			result.add(rs1.getString(9));
			result.add(rs1.getString(10));
			result.add(rs1.getString(11));
			result.add(rs1.getString(12));
		}
		else
			return result;
		result.add("1");
		return result;
	}

	public static ArrayList<String> inventoryCheckExistence(ArrayList<String> data) throws SQLException{
		ArrayList<String> newData = new ArrayList<>();
		newData.add("InventoryCheckExistense");
		if (data.size()==3) {
			String string = "SELECT * FROM book WHERE BookName=? AND AuthorsName=?";
			PreparedStatement checkExistence = conn.prepareStatement(string);
			checkExistence.setString(1, data.get(1));
			checkExistence.setString(2, data.get(2));
			ResultSet rs = checkExistence.executeQuery();

			if(rs.next()) {
				newData.add(rs.getString(1));
				newData.add(rs.getString(2));
				newData.add(rs.getString(3));
				newData.add(rs.getString(4));
				newData.add(rs.getString(5));
				newData.add(rs.getString(6));
				newData.add(rs.getString(7));
				newData.add(rs.getString(8));
				newData.add(rs.getString(9));
				newData.add(rs.getString(10));
				newData.add(rs.getString(11));
				newData.add(rs.getString(12));
			}
			else {
				newData.add("not exist");
			}
		}
		else if (data.size()==2) {
			String string = "SELECT * FROM book WHERE BookID=?";
			PreparedStatement checkExistence = conn.prepareStatement(string);
			checkExistence.setString(1, data.get(1));
			ResultSet rs = checkExistence.executeQuery();
			if(rs.next()) {
				newData.add(rs.getString(1));
				newData.add(rs.getString(2));
				newData.add(rs.getString(3));
				newData.add(rs.getString(4));
				newData.add(rs.getString(5));
				newData.add(rs.getString(6));
				newData.add(rs.getString(7));
				newData.add(rs.getString(8));
				newData.add(rs.getString(9));
				newData.add(rs.getString(10));
				newData.add(rs.getString(11));
				newData.add(rs.getString(12));
			}
			else {
				newData.add("not exist");
			}
		}
		return newData;
	}

	public static ArrayList<String>  login(ArrayList<String> data) throws SQLException
	{
		ArrayList<String> userDetails = null;
		PreparedStatement login;
		ResultSet rs;
		//First we search in librarian table if the user is librarian
		login = conn.prepareStatement("SELECT LibrarianID,Password,FirstName,LastName,IsLoggedIn,IsManager FROM librarian WHERE LibrarianID=? AND Password=?");
		login.setString(1,data.get(1));//LibrarianID
		login.setString(2,data.get(2));//Password
		rs = login.executeQuery();

		if(rs.next()) {
			//If the user is a librarian
			userDetails=new ArrayList<String>();
			userDetails.add("Login");
			userDetails.add(rs.getString(1));//Add LibrarianID
			userDetails.add(rs.getString(2));//Add Password
			userDetails.add(rs.getString(3));//Add FirstName
			userDetails.add(rs.getString(4));//Add LastName
			//System.out.println(userDetails+"userDetails");


			//////////////////Check if user (librarian) is connected from another device

			if(rs.getString(5).equals("true")) { 
				//System.out.println("librarian is already connceted from another device");
				userDetails.add("0");//Add 0 to the arrayList to identify that the user is already connected
				return userDetails;
			}
			else {
				//If the librarian isn't connected - update IsLoggedIn to 'true' (connected)
				login = conn.prepareStatement("UPDATE librarian SET IsLoggedIn='true' where LibrarianID=?");
				login.setString(1, data.get(1));
				login.executeUpdate();
				//System.out.println("librarian connceted");
			}
			userDetails.add("1");//Add 1 to the arrayList to identify that the librarian is connected
			userDetails.add(rs.getString(6));
			return userDetails;
		}

		else {
			//If the user is a member
			userDetails=new ArrayList<String>();
			login = conn.prepareStatement("SELECT * FROM members WHERE MemberID=? AND Password=?");
			login.setString(1,data.get(1));//MemberID
			login.setString(2,data.get(2));//Password
			rs = login.executeQuery();
			if(rs.next()) {
				userDetails.add("Login");
				userDetails.add(rs.getString(1));//Add MemberID
				userDetails.add(rs.getString(4));//Add Password
				userDetails.add(rs.getString(5));//Add FirstName
				userDetails.add(rs.getString(6));//Add LastName

				//////////////////Check if user (member) is connected from another device

				if(rs.getString(10).equals("true")) { 
					//System.out.println("librarian is already connceted from another device");
					userDetails.add("0");//Add 0 to the arrayList to identify that the member is already connected
					return userDetails;
				}
				else {
					login = conn.prepareStatement("UPDATE members SET IsLoggedIn='true' where MemberID=?");
					login.setString(1, data.get(1));
					login.executeUpdate();
				}

				if(rs.getString(12).equals("true")) { 
					userDetails.add("3");
					return userDetails;
				}
				userDetails.add("2");//Add 2 to the arrayList to identify that the member is connected
				return userDetails;
			}
			else {
				//Enter null values if the user doesn't exists
				userDetails.add("Login");
				userDetails.add(null);
				userDetails.add(null);
				userDetails.add(null);
				userDetails.add(null);
				userDetails.add("-1");
				return userDetails;
			}	
		}
	}

	public static ArrayList<String> editBook(ArrayList<String> searchData) throws SQLException{
		int answer;
		PreparedStatement updatebook=conn.prepareStatement("UPDATE book SET BookName=?,EditionNumber=?,BookGenre=?,PDFLink=?,AuthorsName=?,ShelfLocation=?,Description=?,Wanted=?,PurchaseDate=?,PrintDate=? WHERE BookID=?");
		updatebook.setString(1, searchData.get(1));	
		updatebook.setString(2, searchData.get(2));	
		updatebook.setString(3, searchData.get(3));	
		updatebook.setString(4, searchData.get(4));	
		updatebook.setString(5, searchData.get(5));	
		updatebook.setString(6, searchData.get(6));	
		updatebook.setString(7, searchData.get(7));	
		updatebook.setString(8, searchData.get(8));	
		updatebook.setString(9, searchData.get(9));	
		updatebook.setString(10, searchData.get(10));	
		updatebook.setString(11, searchData.get(11));	
		answer=updatebook.executeUpdate();
		searchData.add(Integer.toString(answer));
		return searchData;
	}


	public static ArrayList<String> searchBook(ArrayList<String> searchData) throws SQLException
	{
		PreparedStatement searchBook;
		ResultSet rsBook;
		switch (searchData.get(1)) {
		case "Book Name":
			searchBook = conn.prepareStatement("SELECT BookName,AuthorsName FROM book WHERE BookName=? ");
			searchBook.setString(1,searchData.get(2));
			rsBook = searchBook.executeQuery();
			if (!(rsBook.isBeforeFirst()))
			{
				searchData.add("-1");
				return searchData; 		// the book not found, handle with this....
			}
			else {
				searchData.add("1");
				while(rsBook.next())
				{
					searchData.add(rsBook.getString(1));
					searchData.add(rsBook.getString(2));
				}
				return searchData;
			}
		case "Authors Name":
			searchBook = conn.prepareStatement("SELECT BookName,AuthorsName FROM book WHERE AuthorsName=? ");
			searchBook.setString(1,searchData.get(2));
			rsBook = searchBook.executeQuery();
			if (!(rsBook.isBeforeFirst()))
			{
				searchData.add("-1");
				return searchData; 		// the book not found, handle with this....
			}
			else {
				searchData.add("1");
				while(rsBook.next())
				{
					searchData.add(rsBook.getString(1));
					searchData.add(rsBook.getString(2));
				}
				return searchData;
			}
		case "Book Theme":
			searchBook = conn.prepareStatement("SELECT BookName,AuthorsName FROM book WHERE BookGenre=? ");
			searchBook.setString(1,searchData.get(2));
			rsBook = searchBook.executeQuery();
			if (!(rsBook.isBeforeFirst()))
			{
				searchData.add("-1");
				return searchData; 		// the book not found, handle with this....
			}
			else {
				searchData.add("1");
				while(rsBook.next())
				{
					searchData.add(rsBook.getString(1));
					searchData.add(rsBook.getString(2));
				}
				return searchData;
			}	
		case "Free text":
			searchBook = conn.prepareStatement("SELECT BookName,AuthorsName FROM book WHERE Description=? ");
			searchBook.setString(1,searchData.get(2));
			rsBook = searchBook.executeQuery();
			if (!(rsBook.isBeforeFirst()))
			{
				searchData.add("-1");
				return searchData; 		// the book not found, handle with this....
			}
			else {
				searchData.add("1");
				while(rsBook.next())
				{
					searchData.add(rsBook.getString(1));
					searchData.add(rsBook.getString(2));
				}
				return searchData;
			}	
		default:
			searchData.add("-1");
			break;
		}
		return searchData;
	}

	public static Object memberSearch(ArrayList<String> stu) throws SQLException
	{
		PreparedStatement execute;
		ResultSet rs;
		ArrayList<String>member=new ArrayList<String>();
		member.add("SearchMember");
		execute = conn.prepareStatement("SELECT * FROM members WHERE MemberID=?");
		execute.setString(1,stu.get(1));
		rs = execute.executeQuery();
		if(rs.next()) { 
			member.add(rs.getString(1));
			member.add(rs.getString(2));
			member.add(rs.getString(3));
			member.add(rs.getString(4));
			member.add(rs.getString(5));
			member.add(rs.getString(6));
			member.add(rs.getString(7));
			member.add(rs.getString(8));
			member.add(rs.getString(9));
			member.add(rs.getString(10));
			member.add(rs.getString(11));
			member.add(rs.getString(12));
			System.out.println(member);
			return member;
		}
		else
			return null;
	}


	public static ArrayList<String> isMemberExist(ArrayList<String> data) throws SQLException {
		ArrayList<String> checkMemberExistence = new ArrayList<>();
		checkMemberExistence.add("Check Member Existence");
		ResultSet rs;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM members WHERE MemberID = ?");
		ps.setString(1, data.get(1));
		rs = ps.executeQuery();
		if (!rs.isBeforeFirst() ) {    
			return checkMemberExistence;
		}
		if(rs.next()) {
			checkMemberExistence.add(rs.getString(1));
			checkMemberExistence.add(rs.getString(2));
			checkMemberExistence.add(rs.getString(3));
			checkMemberExistence.add(rs.getString(4));
			checkMemberExistence.add(rs.getString(5));
			checkMemberExistence.add(rs.getString(6));
			checkMemberExistence.add(rs.getString(7));
			checkMemberExistence.add(rs.getString(8));
			checkMemberExistence.add(rs.getString(9));
			checkMemberExistence.add(rs.getString(10));
			checkMemberExistence.add(rs.getString(11));
			checkMemberExistence.add(rs.getString(12));
		}
		return checkMemberExistence;
	}


	public static ArrayList<String> isCopyLoaned(ArrayList<String> data) throws SQLException {
		ArrayList<String> checkCopyLoanStatus = new ArrayList<>();
		checkCopyLoanStatus.add("Check Copy Loan Status");
		ResultSet rs;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM loanbook WHERE CopyID = ?");
		ps.setString(1, data.get(1));
		rs = ps.executeQuery();
		if (!rs.isBeforeFirst() ) {    
			return checkCopyLoanStatus;
		}
		if(rs.next()) {
			checkCopyLoanStatus.add(rs.getString(1));
			checkCopyLoanStatus.add(rs.getString(2));
			checkCopyLoanStatus.add(rs.getString(3));
			checkCopyLoanStatus.add(rs.getString(4));
			checkCopyLoanStatus.add(rs.getString(5));
			checkCopyLoanStatus.add(rs.getString(6));
			checkCopyLoanStatus.add(rs.getString(7));
			checkCopyLoanStatus.add(rs.getString(8));
		}
		return checkCopyLoanStatus;
	}

	public static ArrayList<String> isCopyExist(ArrayList<String> data) throws SQLException {
		ArrayList<String> checkCopyLoanStatus = new ArrayList<>();
		checkCopyLoanStatus.add("Check Copy ID Existence");
		ResultSet rs;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM copies WHERE CopyID = ?");
		ps.setString(1, data.get(1));
		rs = ps.executeQuery();
		if (!rs.isBeforeFirst() ) {    
			return checkCopyLoanStatus;
		}
		if(rs.next()) {
			checkCopyLoanStatus.add(rs.getString(1));
			checkCopyLoanStatus.add(rs.getString(2));
			checkCopyLoanStatus.add(rs.getString(3));
			checkCopyLoanStatus.add(rs.getString(4));
			checkCopyLoanStatus.add(rs.getString(5));
		}
		return checkCopyLoanStatus;
	}

	public static ArrayList<String> returnBook(ArrayList<String> data) throws SQLException {
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		ArrayList<String> returnBook = new ArrayList<>();
		returnBook.add("Return Book");
		PreparedStatement ps = conn.prepareStatement("UPDATE copies SET isLoaned = ?, ActualReturnDate = ? WHERE CopyID = ?");
		ps.setString(1, "false");
		ps.setString(2, currentTime);
		ps.setString(3, data.get(1));
		PreparedStatement ps1 = conn.prepareStatement("UPDATE loanbook SET isReturned = ?, ActualReturnDate = ? WHERE CopyID = ? AND isReturned = ?");
		ps1.setString(1, "true");
		ps1.setString(2, currentTime);
		ps1.setString(3, data.get(1));
		ps1.setString(4, "false");


		if(!data.get(2).equals("Active")) {
			String memberID = null;
			String copyID = null;
			String newCopyID = null;
			ResultSet rs;

			PreparedStatement ps3 = conn.prepareStatement("SELECT MemberID from delayonreturn WHERE CopyID = ?");
			ps3.setString(1, data.get(1));
			rs = ps3.executeQuery();
			if(rs.next()) {
				memberID = rs.getString(1);
			}

			PreparedStatement ps4 = conn.prepareStatement("DELETE from delayonreturn WHERE CopyID = ?");
			ps4.setString(1, data.get(1));
			ps4.executeUpdate();

			PreparedStatement ps5 = conn.prepareStatement("SELECT FreezedOn from members WHERE MemberID = ?");
			ps5.setString(1, memberID);
			rs = ps5.executeQuery();
			if(rs.next()) {
				copyID = rs.getString(1);
			}

			PreparedStatement ps6 = conn.prepareStatement("SELECT CopyID from delayonreturn WHERE MemberID = ?");
			ps6.setString(1, memberID);
			rs = ps6.executeQuery();
			if (!rs.isBeforeFirst() ) {    
				newCopyID = null;
			}
			else {
				rs.next();
				newCopyID = rs.getString(1);
			}


			if(data.get(1).equals(copyID) && newCopyID != null && memberID != null) {
				PreparedStatement ps7 = conn.prepareStatement("UPDATE members SET FreezedOn = ? WHERE MemberID = ?");
				ps7.setString(1, newCopyID);
				ps7.setString(2, memberID);
				ps7.executeUpdate();
			}
			else {
				if(data.get(1).equals(copyID) && newCopyID == null && memberID != null) {
					PreparedStatement ps8 = conn.prepareStatement("UPDATE members SET FreezedOn = ? WHERE MemberID = ?");
					ps8.setString(1, newCopyID);
					ps8.setString(2, memberID);
					ps8.executeUpdate();
				}
			}
		}

		if(ps.executeUpdate() != 0 && ps1.executeUpdate() != 0) {
			returnBook.add(currentTime);
		}

		return returnBook;
	}

	public ArrayList<String> searchBookDetailes(ArrayList<String> msg) throws SQLException{
		ResultSet 	rs1,rs2,rs3;
		String 		bookID   	  = null;
		String		shelfLocation = null;
		String		returnDate	  = null;
		String		memberID	  = null;
		String		copyID	  = null;
		PreparedStatement ps1 = conn.prepareStatement("SELECT BookID,ShelfLocation FROM book WHERE AuthorsName = ? AND BookName = ?");
		ps1.setString(1, msg.get(2));
		ps1.setString(2, msg.get(1));
		rs1 = ps1.executeQuery();
		System.out.println("111111111111111111111111111111111");
		while(rs1.next()) {
			bookID =rs1.getString(1);
			System.out.println("1111111111111111111"+ bookID);
			shelfLocation =rs1.getString(2);
			System.out.println("1111111111111111111" + shelfLocation);
		}
		PreparedStatement ps2 = conn.prepareStatement("SELECT BookID FROM copies WHERE BookID = ? AND IsLoaned = ?");
		ps2.setString(1, bookID);
		ps2.setString(2, "false");
		rs2 = ps2.executeQuery();

		if (!(rs2.isBeforeFirst()) ) // all the copies in loan
		{
			System.out.println("33333333333333");
			java.util.Date date= new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(date);
			System.out.println(currentTime);
			PreparedStatement ps3 = conn.prepareStatement("SELECT ExpectedReturnDate,MemberID,CopyID FROM loanbook WHERE ExpectedReturnDate > ? AND BookID = ? ORDER BY ExpectedReturnDate LIMIT 1");
			ps3.setString(1, currentTime);
			ps3.setString(2, bookID);
			rs3 = ps3.executeQuery();
			while(rs3.next()) {
				returnDate = rs3.getString(1);
				memberID = rs3.getString(2);
				copyID = rs3.getString(3);
				System.out.println("2222222222222222222" + returnDate);
				System.out.println("2222222222222222222" + memberID);
			}
			msg.add("0");
			msg.add(returnDate);
			msg.add(memberID);
			msg.add(bookID);
			msg.add(copyID);
		}
		else {
			System.out.println("7777777777");
			msg.add("1");
			msg.add(shelfLocation);
		}

		return msg;
	}

	public static  void logout(ArrayList<String> data) throws SQLException {
		ArrayList<String> result=new ArrayList<String>();
		PreparedStatement login;
		ResultSet rs;
		login = conn.prepareStatement("SELECT LibrarianID,Password,FirstName,LastName,IsLoggedIn  FROM librarian WHERE LibrarianID=? AND Password=?");
		login.setString(1,data.get(1));
		login.setString(2,data.get(2));
		rs = login.executeQuery();

		if(rs.next()) {
			System.out.println("inside DBcontroller - logout");
			login = conn.prepareStatement("UPDATE librarian SET IsLoggedIn='false' where LibrarianID=?");
			login.setString(1,data.get(1));
			login.executeUpdate();
			System.out.println("inside DBcontroller2 - librarian logged out");
		}

		else {
			login = conn.prepareStatement("SELECT * FROM members WHERE MemberID=? AND Password=?");
			login.setString(1,data.get(1));
			login.setString(2,data.get(2));
			rs = login.executeQuery();

			if(rs.next()) {
				login = conn.prepareStatement("UPDATE members SET IsLoggedIn='false' where MemberID=?");
				login.setString(1,data.get(1));
				login.executeUpdate();
			}
		}
		result.add("Logout");
		//return result;
	}

	public static ArrayList<String> isMemberLateOnReturn(ArrayList<String> data) throws SQLException {
		ArrayList<String> checkMemberReturns = new ArrayList<>();
		checkMemberReturns.add("Check If Member Is Late On Return");
		ResultSet rs;
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM delayonreturn WHERE MemberID = ?");
		ps.setString(1, data.get(1));
		rs = ps.executeQuery();
		if (rs.next()) {
			checkMemberReturns.add(Integer.toString(rs.getInt(1)));
		}
		else {
			checkMemberReturns.add("0");
		}
		return checkMemberReturns;
	}


	public static ArrayList<String> changeMemberStatus(ArrayList<String> data) throws SQLException {
		ArrayList<String> changeStatus = new ArrayList<>();
		changeStatus.add("Change Member Status");
		PreparedStatement ps = conn.prepareStatement("UPDATE members SET Status = ? WHERE MemberID = ?");
		ps.setString(1, data.get(2));
		ps.setString(2, data.get(1));
		if(ps.executeUpdate() != 0) {
			changeStatus.add(data.get(2));
		}
		return changeStatus;
	}
	public static ArrayList<String> CheckLibrarianManager(ArrayList<String> msg) throws SQLException {
		ArrayList<String> CheckLibrarianManager = new ArrayList<String>();
		CheckLibrarianManager.add("CheckLibrarianManager");
		ResultSet rs=null;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM librarian WHERE LibrarianID = ?");
		ps.setString(1,((String)msg.get(1)));
		rs = ps.executeQuery();
		if(rs.next()) {
			CheckLibrarianManager.add(rs.getString(7));
			System.out.println(CheckLibrarianManager);
			return CheckLibrarianManager;

		}
		else
			return null;
	}
	public void MemberUpdateMemberDetails(ArrayList<String> member) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE members SET PhoneNumber = ?, Email = ? WHERE MemberID = ?");
		ps.setString(1, member.get(2));
		ps.setString(2, member.get(3));
		ps.setString(3, member.get(1));
		ps.executeUpdate();
	}
	public void librarianUpdateMember(ArrayList<String> member) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE members SET Status = ?, Notes = ? WHERE MemberID = ?");
		ps.setString(1, member.get(2));
		ps.setString(2, member.get(3));
		ps.setString(3, member.get(1));
		ps.executeUpdate();
	}
	public void viewPersonalHistory(ArrayList<String> searchData) throws SQLException {
		PreparedStatement searchLoan;
		ResultSet rsLoan;
		searchLoan = conn.prepareStatement("SELECT CopyID FROM loanbook WHERE MemberID=? ");
		searchLoan.setString(1,searchData.get(2));
		rsLoan = searchLoan.executeQuery();
		if (!(rsLoan.isBeforeFirst()))
		{
			searchData.add("-1");	// the no loan were found, handle with this....
			//return searchData; 		
		}
	}

	public ArrayList<String> reserveBook(ArrayList<String> bookdata) throws SQLException {
		int copies,answer,reserveamount;
		String bookID=bookdata.get(1),currentTime;
		String copyID = null;
		System.out.println(bookID);
		PreparedStatement ps = conn.prepareStatement("SELECT copies FROM book WHERE BookID = ?");
		ps.setString(1,bookID);
		ResultSet rs= ps.executeQuery();
		if(rs.next()) {
			copies=rs.getInt(1);
		}
		else {
			System.out.println("cannot retrieve copies from book table.");
			bookdata.add("fail-1");
			return bookdata;
		}
		System.out.println(copies);
		PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM reservations WHERE BookID=? AND IsActive='true' ");
		ps2.setString(1,bookID);
		ResultSet rs1= ps2.executeQuery();
		if(rs1.next()) {
			reserveamount=rs1.getInt(1);
			System.out.println(reserveamount);
		}
		else {
			bookdata.add("all the copies are allready reserved.");
			return bookdata;
		}

		if (reserveamount<copies) {
			currentTime=CommonController.getCurrentTime();
			PreparedStatement ps3 = conn.prepareStatement("SELECT CopyID FROM loanbook WHERE ExpectedReturnDate > ? AND BookID = ? AND IsReserved='false' ORDER BY ExpectedReturnDate LIMIT 1");
			ps3.setString(1, currentTime);
			ps3.setString(2, bookID);
			ResultSet rs2=ps3.executeQuery();
			if (rs2.next()) {
				copyID=rs2.getString(1);
			}
			else {
				bookdata.add("fail-2");
				return bookdata;
			}
			PreparedStatement insert = conn.prepareStatement("insert into reservations values(?,?,?,?,?)");
			insert.setString(1, copyID);
			insert.setString(2, null);
			insert.setString(3, bookdata.get(2));
			insert.setString(4, bookID);
			insert.setString(5, "true");
			answer=insert.executeUpdate();
			if (answer==1) {
				bookdata.add("success");
			}
			PreparedStatement update = conn.prepareStatement("UPDATE loanbook SET IsReserved = ? WHERE CopyID = ?");
			update.setString(1, "true");
			update.setString(2, copyID);
			answer=update.executeUpdate();
			if (answer!=1) {
				bookdata.add("fail-3");
			}
		}
		else bookdata.add("all the copies are allready reserved.");
		return bookdata;	
	}


	private static Connection connectToDatabase() {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error*/}

		try 
		{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/obl","root","Aa123456");
			System.out.println("SQL connection succeed");
			return conn;
		} catch (SQLException ex) 
		{	/* handle any errors*/
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}
}
