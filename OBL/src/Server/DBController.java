package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.UpdatableResultSet;
import com.sun.glass.ui.TouchInputSupport;
import Client.Client;
import Common.Book;
import Common.InventoryBook;
import jdk.nashorn.internal.ir.LoopNode;
import sun.applet.resources.MsgAppletViewer;




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
			return 2;
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
		PreparedStatement insert = conn.prepareStatement("insert into copies values(?,?,?,?,?,?)");
		copyid=(data.get(data.size()-1)) + "-" + Integer.toString(++maxCopyID);
		insert.setString(1, copyid);
		insert.setString(2, data.get(1));
		insert.setString(3, "false");
		insert.setString(4, data.get(2));
		insert.setString(5, data.get(data.size()-1));
		insert.setString(6, null);
		insert.executeUpdate();
		data.add(copyid);
		data.add("success");
		updatecopyamount("increase",bookid);
		return data;
	}

	public static void updatecopyamount(String action,String bookid) throws SQLException {
		int amountcopies=0;
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
			break;
		default:
			break;
		}

	}


	public static int RemoveCopy(ArrayList<String> data) throws SQLException{
		String bookID;
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
			updatecopyamount("decrease",bookID);
		}
		return res;
	}

	public static ArrayList<String> checkExistenceByCopy(ArrayList<String> msg) throws SQLException {
		String bookid,location;
		ArrayList<String> result=new ArrayList<String>();
		result.add("checkExistenceByCopy");
		PreparedStatement getbook = conn.prepareStatement("SELECT BookID,ShelfLocation FROM copies WHERE CopyID=?");
		getbook.setString(1, msg.get(1));
		ResultSet rs = getbook.executeQuery();
		if(rs.next()) {
			bookid=(rs.getString(1));
			location=rs.getString(2);
		}
		else
			return result;
		PreparedStatement getbookdetails=conn.prepareStatement("SELECT * FROM book WHERE bookID=?");
		getbookdetails.setString(1, bookid);
		ResultSet rs1=getbookdetails.executeQuery();
		if(rs1.next()) {
			result.add(location);
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
		}
		else
			return result;
		result.add("1");
		return result;
	}
	//		ArrayList<String> data=new ArrayList<String>();
	//		ArrayList<String> data1=new ArrayList<String>();
	//		String BookName=null;
	//		String AuthorsName=null;
	//		data1=isCopyExist(msg);// return all the details of copy
	//		PreparedStatement getbookdetails = conn.prepareStatement("SELECT BookName,AuthorsName FROM book WHERE BookID=? ");
	//		getbookdetails.setString(1, data1.get(5));
	//		ResultSet rs = getbookdetails.executeQuery();
	//		if(rs.next()) {
	//			BookName=rs.getString(1);
	//			AuthorsName=rs.getString(2);
	//		}
	//		data.add("checkExistenceByCopy");
	//		data.add(1, BookName);
	//		data.add(2,AuthorsName);
	//		data1.clear();
	//		data1= inventoryCheckExistence(data);
	//		return data1;
	//	}

	//search book on inventory database
	public static ArrayList<String> inventoryCheckExistence(ArrayList<String> data) throws SQLException{
		ArrayList<String> newData = new ArrayList<>();
		newData.add("InventoryCheckExistense");
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
		}
		else {
			newData.add("not exist");
		}
		rs.close();
		checkExistence.close();
		String string1 = "SELECT ShelfLocation FROM copies WHERE BookID=?";
		PreparedStatement getcopyloaction = conn.prepareStatement(string1);
		getcopyloaction.setString(1, newData.get(1));
		ResultSet rs1 = getcopyloaction.executeQuery();
		if(rs1.next()) {
			newData.add(rs1.getString(1));
		}
		rs1.close();
		getcopyloaction.close();
		return newData;
	}

	public static ArrayList<String>  login(ArrayList<String> data) throws SQLException
	{
		ArrayList<String> userDetails = null;
		PreparedStatement login;
		ResultSet rs;
		login = conn.prepareStatement("SELECT LibrarianID,Password,FirstName,LastName,IsLoggedIn  FROM librarian WHERE LibrarianID=? AND Password=?");
		login.setString(1,data.get(1));
		login.setString(2,data.get(2));
		rs = login.executeQuery();

		if(rs.next()) {
			userDetails=new ArrayList<String>();
			userDetails.add("Login");
			userDetails.add(rs.getString(1));
			userDetails.add(rs.getString(3));
			userDetails.add(rs.getString(4));
			System.out.println(userDetails+"userDetails");
			//////////////////Enter librarian menu
			if(rs.getString(5).equals("true")) { //check if user is connected from another device
				System.out.println("librarian is already connceted from another device");
				userDetails.add("0");
				return userDetails;
			}
			userDetails.add("1");
			return userDetails;
		}

		else {
			userDetails=new ArrayList<String>();
			login = conn.prepareStatement("SELECT * FROM members WHERE MemberID=? AND Password=?");
			login.setString(1,data.get(1));
			login.setString(2,data.get(2));
			rs = login.executeQuery();
			if(rs.next()) {
				userDetails.add("Login");
				userDetails.add(rs.getString(1));
				userDetails.add(rs.getString(5));
				userDetails.add(rs.getString(6));
				if(rs.getString(10).equals("true")) { //check if user is connected from another device
					System.out.println("librarian is already connceted from another device");
					userDetails.add("0");
					return userDetails;
				}
				userDetails.add("2");
				return userDetails;
			}
			else {
				System.out.println("The User doesn't exists");
				return userDetails;
			}	
		}
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

		execute = conn.prepareStatement("SELECT * FROM members WHERE MemberID=?");
		execute.setString(1,stu.get(1));
		rs = execute.executeQuery();
		if(rs.next()) { 
			member.add("SearchMember");
			member.add(rs.getString(1));
			member.add(rs.getString(2));
			member.add(rs.getString(3));
			member.add(rs.getString(4));
			member.add(rs.getString(5));
			member.add(rs.getString(6));
			member.add(rs.getString(7));
			member.add(rs.getString(8));
			member.add(rs.getString(9));
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
			checkCopyLoanStatus.add(rs.getString(6));
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
		PreparedStatement ps2 = conn.prepareStatement("SELECT BookID FROM copies WHERE BookID = ? AND IsLoan = ?");
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
			PreparedStatement ps3 = conn.prepareStatement("SELECT ExpectedReturnDate,MemberID FROM loanbook WHERE ExpectedReturnDate > ? AND BookID = ? ORDER BY ExpectedReturnDate LIMIT 1");
			ps3.setString(1, currentTime);
			ps3.setString(2, bookID);
			rs3 = ps3.executeQuery();
			while(rs3.next()) {
				returnDate = rs3.getString(1);
				memberID = rs3.getString(2);
				System.out.println("2222222222222222222" + returnDate);
				System.out.println("2222222222222222222" + memberID);
			}
			msg.add("0");
			msg.add(returnDate);
			msg.add(memberID);
			msg.add(bookID);
		}
		else {
			System.out.println("7777777777");
			msg.add("1");
			msg.add(shelfLocation);
		}
		
		return msg;
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
