package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.glass.ui.TouchInputSupport;
import Client.Client;
import Common.Book;
import Common.InventoryBook;
import jdk.nashorn.internal.ir.LoopNode;




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
		PreparedStatement insert = conn.prepareStatement("insert into book values(?,?,?,?,?,?,?,?,?,?,?)");
		insert.setString(1, Integer.toString(++maxBookID));
		insert.setString(2, data.get(1));
		insert.setString(3, "1");
		insert.setString(4, data.get(3));
		insert.setString(5, data.get(4));
		insert.setString(6, data.get(5));
		insert.setString(7, data.get(6));
		insert.setString(8, data.get(7));
		insert.setString(9, data.get(8));
		insert.setString(10, data.get(9));
		insert.setString(11, "pdf");
		Result=insert.executeUpdate();
		data.add(Integer.toString(maxBookID));
		addCopyToInventory(data);
		return Result;
	}

	public static void addCopyToInventory(ArrayList<String> data) throws SQLException{
		int maxCopyID=0;
		PreparedStatement getCopyID = conn.prepareStatement("SELECT MAX(CopyID) FROM copies WHERE BookID = ?");
		getCopyID.setString(1, data.get(data.size()-1));
		ResultSet rs = getCopyID.executeQuery();

		if(rs.next()) {
			if (rs.getString(1)!=null) {
				maxCopyID=Integer.parseInt(rs.getString(1));
			}
		}

		PreparedStatement insert = conn.prepareStatement("insert into copies values(?,?,?,?,?,?)");
		insert.setString(1, (data.get(data.size()-1)) + " - " + Integer.toString(++maxCopyID));
		insert.setString(2, data.get(1));
		insert.setString(3, "false");
		insert.setString(4, data.get(10));
		insert.setString(5, data.get(data.size()-1));
		insert.setString(6, "null");
		insert.executeUpdate();
	}

	public static int RemoveCopy(ArrayList<String> data) throws SQLException{
		String deleteSQL = "DELETE FROM copies WHERE CopyID = ?";
		PreparedStatement removestmt = conn.prepareStatement(deleteSQL);
		removestmt.setString(1, data.get(1));
		int res=removestmt.executeUpdate();
		removestmt.close();
//		conn.close();
		return res;

	}
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
		System.out.println(newData);
		return newData;
	}

	public static int login(ArrayList<String> data) throws SQLException
	{
		PreparedStatement login;
		ResultSet rs;

		login = conn.prepareStatement("SELECT LibrarianID,Password FROM librarian WHERE LibrarianID=? AND Password=?");
		//		login = conn.prepareStatement("SELECT LibrarianID FROM librarian WHERE LibrarianID=? ");
		login.setString(1,data.get(1));
		login.setString(2,data.get(2));
		rs = login.executeQuery();

		if(rs.next()) {
			//////////////////Enter librarian menu
			return 1;
		}

		else {
			login = conn.prepareStatement("SELECT MemberID,Password FROM members WHERE MemberID=? AND Password=?");
			login.setString(1,data.get(1));
			login.setString(2,data.get(2));
			rs = login.executeQuery();
			if(rs.next()) {
				/////////////////////Enter subscriber menu
				return 2;
			}
			else {
				System.out.println("The User doesn't exists");
				return 0;
			}	
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
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

			
//			searchCopy = conn.prepareStatement("SELECT * FROM copies WHERE CopyName=? AND ISLoan = 'No'");
//			searchCopy.setString(1,searchData.get(2));
//			rsCopy = searchCopy.executeQuery();
//			
//			if (rsCopy != null)
//			{								// have copy in the library, handle with this...
//				while (rsCopy.next())
//				{
//					
//				}
//								
//			}
//			
//			searchCopy = conn.prepareStatement("SELECT * DISTINCT () FROM copies WHERE CopyName=? AND ISLoan = 'Yes' And ReturnDate > NOW() ORDER BY ReturnDate");
//			searchCopy.setString(1,searchData.get(2)); 	// fix the query distinct on what? , who to get the nearest date to all different books?
//			rsCopy = searchCopy.executeQuery();
//			
//			while(rsCopy.next())
//			{
//			
//			}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

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
