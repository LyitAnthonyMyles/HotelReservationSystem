package project.lyit.hotel;

import java.sql.*;

public class DatabaseConnector {

	Connection conn = null;
	Statement stmt = null;
	
	
	public void addRoom(int roomNo, String roomType, boolean decomissioned ) {
		conn = connectToDatabase();
		String sql = "INSERT INTO room VALUES (" + roomNo + ", '" + roomType  + "', " +  decomissioned +  ")";
 
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Room ADDED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}
	
	public void editRoom(int roomNo, String roomType, boolean decomm) {
		conn = connectToDatabase();
		String sql = "UPDATE room SET RoomType='" + roomType + "', Decommissioned= " + decomm
				+ " WHERE RoomNo=" + roomNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Room UPDATED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}

	public void deleteRoom(int roomNo) {
		conn = connectToDatabase();
		String sql = "DELETE FROM room WHERE RoomNo=" + roomNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Room DELETED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}

	public void addCustomer(int custNo, String fName, String lName, String addr, String phone, String email) {
		conn = connectToDatabase();
	 	String sql = "INSERT INTO customer VALUES (" + custNo + ", '"
	 			+ fName + "', '" + lName + "', '" + addr + "', '" + phone + "', '" + email + "')";
	 	try {
	 		stmt = conn.createStatement();
	 		stmt.executeUpdate(sql);
	 		System.out.println("SUCCESS!!!");
	 	} catch (SQLException e) {
	 		e.printStackTrace();
	 	} finally {
			closeDatabaseConnection();
	 	}
	}
	
	public void editCustomer(int custNo, String fName, String lName, String addr, String phone, String email) {
		conn = connectToDatabase();
		String sql = "UPDATE customer SET FirstName='" + fName + "', LastName='" + lName + 
					"', Address= '" + addr + "', Phone='" + phone + "', Email='" + email + "' WHERE CustomerNo=" + custNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Customer UPDATED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}

	public void deleteCustomer(int custNo) {
		conn = connectToDatabase();
		String sql = "DELETE FROM customer WHERE CustomerNo=" + custNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Customer DELETED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}
	
	public void addExtra(int extraNo, String type, int qty, double cost, int bookingNo) {
		conn = connectToDatabase();
	 	String sql = "INSERT INTO extra VALUES (" + extraNo + ", '"
	 			+ type + "', " + qty + ", " + cost + ", " + bookingNo + ")";
	 	try {
	 		stmt = conn.createStatement();
	 		stmt.executeUpdate(sql);
	 		System.out.println("SUCCESS!!!");
	 	} catch (SQLException e) {
	 		e.printStackTrace();
	 	} finally {
			closeDatabaseConnection();
	 	}
	}
	
	public void editExtra(int extraNo, String type, int qty, double cost, int bookingNo) {
		conn = connectToDatabase();
		String sql = "UPDATE customer SET Type='" + type + "', Quantity=" + qty + 
					", Cost= " + cost + ", BookingNo=" + bookingNo + " WHERE ExtraNo=" + extraNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Extra UPDATED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}

	public void deleteExtra(int extraNo) {
		conn = connectToDatabase();
		String sql = "DELETE FROM extra WHERE CustomerNo=" + extraNo;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Extra DELETED!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabaseConnection();
		}
	}
	
	private Connection connectToDatabase() {
		// database URL and credentials
		final String DB_URL = "jdbc:mysql://localhost/hotel_reservation_system";
		final String USER = "root";
		final String PASS = "";
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	
		return conn;
	}
	
	private void closeDatabaseConnection() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
