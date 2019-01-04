package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
	
	
	public static void registretion(ArrayList<String> data) throws SQLException
	{
		System.out.println(data);
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
	
	public static int login(ArrayList<String> data) throws SQLException
	{
		PreparedStatement login;
		ResultSet rs;
		
		login = conn.prepareStatement("SELECT LibrarianID,Password FROM librarian WHERE LibrarianID=? AND Password=?)");
		login.setString(1,data.get(1));
		login.setString(2,data.get(2));
		
		rs = login.executeQuery();
		
		if(rs != null) {
			//////////////////Enter librarian menu
			return 1;
		}
		
		else {
			login = conn.prepareStatement("SELECT MemberID,Password FROM members WHERE MemberID=? AND Password=?)");
			login.setString(1,data.get(2));
			login.setString(2,data.get(3));
			
			rs = login.executeQuery();
			if(rs != null) {
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
