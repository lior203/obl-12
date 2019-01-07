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
	public static void registretion(ArrayList<String> data) throws SQLException
	{
		PreparedStatement insert = conn.prepareStatement("insert into members values(?,?,?,?,?,?,?,?)");
		insert.setString(1, data.get(2));
		insert.setString(2, data.get(1));
		insert.setString(3, data.get(5));
		insert.setString(4, data.get(6));
		insert.setString(5, data.get(4));
		insert.setString(6, data.get(3));
		insert.setString(7, "Active");
		insert.setString(8, "empty");
		insert.executeUpdate();
	}
	//add book to inventory database
	public static int AddBook(ArrayList<String> data) throws SQLException{
		int Result;
		PreparedStatement insert = conn.prepareStatement("insert into book values(?,?,?,?,?,?,?,?,?,?,?,?)");
		insert.setString(1, data.get(1));
		insert.setString(2, data.get(2));
		insert.setString(3, data.get(3));
		insert.setString(4, data.get(4));
		insert.setString(5, data.get(5));
		insert.setString(6, data.get(6));
		insert.setString(7, data.get(7));
		insert.setString(8, data.get(8));
		insert.setString(9, data.get(9));
		insert.setString(10, data.get(10));
		insert.setString(11, data.get(11));
		insert.setString(12, "pdf");
		Result=insert.executeUpdate();
		return Result;
	}
	
	public static int RemoveBook(ArrayList<String> data) throws SQLException{
		String deleteSQL = "DELETE FROM book WHERE BookID = ?";
		PreparedStatement removestmt = conn.prepareStatement(deleteSQL);
		removestmt.setString(1, data.get(1));
		int res=removestmt.executeUpdate();
		removestmt.close();
		conn.close();
		return res;
		
	}
	//search book on inventory database
	public static ArrayList<String> SearchBook(ArrayList<String> data) throws SQLException{
		Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE BookID = "+"'" + data.get(1)+"'");
    	if(rs.next()) {
    			data.add(rs.getString(2));
    			data.add(rs.getString(3));
    			data.add(rs.getString(4));
    			data.add(rs.getString(5));
    			data.add(rs.getString(6));
    			data.add(rs.getString(7));
    			data.add(rs.getString(8));
    			data.add(rs.getString(9));
    			data.add(rs.getString(10));
    			data.add(rs.getString(11));
    	}
    	System.out.println(data);
    	rs.close();
    	stmt.close();
    	return data;
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
