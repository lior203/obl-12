package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		PreparedStatement insert = conn.prepareStatement("INSERT INTO members(PhoneNumber,MemberID,LastName,FirstName,Email,Password,Status) VALUES (?,?,?,?,?,?,?,?)");
		insert.setString(1, data.get(2));
		insert.setString(2, data.get(3));
		insert.setString(3, data.get(4));
		insert.setString(4, data.get(5));
		insert.setString(5, data.get(6));
		insert.setString(6, data.get(7));
		insert.setString(7, data.get(8));
		insert.setString(8, "Active");
		insert.executeUpdate();
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
