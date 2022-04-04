package project.lyit.hotel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

public class Booking {
	
	private DatabaseConnector dbConnect;
	private Connection conn;
	private Statement stmt;
	
	public Booking() {
		dbConnect = new DatabaseConnector();
	}

	public ArrayList<String> getBookingAvailability(LocalDate checkDate, LocalDate checkoutDate, String type) {	
		conn = dbConnect.connectToDatabase();
		//ArrayList<Integer> existingNums = room.getExistingRooms();
		ArrayList<Integer> unavailableNums = new ArrayList<>();
		ArrayList<String> availableToBook = new ArrayList<>();
		ArrayList<String> availability = new ArrayList<>();

		String sqlUnavailable = "SELECT RoomNo from booking WHERE (CheckIn <= '" + checkDate.toString()
					+ "' AND CheckOut >= '" + checkDate.toString() + "') AND (CheckIn <= '" + checkoutDate.toString()
					+ "' AND CheckOut >= '" + checkoutDate.toString() + "')";
		
		//SELECT RoomNo from booking WHERE (CheckIn <= '2022/04/01' AND CheckOut >= '2022/04/01') AND (CheckIn <= '2022/04/03' AND CheckOut >= '2022/04/03')
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlUnavailable);
			while(rs.next()) {
				unavailableNums.add(rs.getInt("RoomNo"));
			}
			String sqlAvailable = "SELECT * FROM room WHERE RoomType= '" + type + "' AND Decommissioned = false";
			rs = stmt.executeQuery(sqlAvailable);
			while (rs.next()) {
				availability.add("Room: " + rs.getInt("RoomNo") + ", Room Type: " + rs.getString("RoomType"));
			}

			if (unavailableNums.size() > 0) {
				for (int no : unavailableNums) {
					for (String available : availability) {
						if (!(available.contains("" + no))) {
							availableToBook.add(available);
						}
					}
				}
			} else {
				for (String available : availability) {
					availableToBook.add(available);
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				dbConnect.closeDatabaseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return availableToBook;
	}
	
	public ArrayList<Integer> getExistingBookings() {
		ArrayList<Integer> existingBookings = new ArrayList<>();
		conn = dbConnect.connectToDatabase();
		String sql = "SELECT * FROM booking";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				existingBookings.add(rs.getInt("BookingNo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				dbConnect.closeDatabaseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return existingBookings;
	}

	public int getNextNo() {
		conn = dbConnect.connectToDatabase();
		String sql = "SELECT * FROM booking";
		int lastNo = 20000;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				lastNo = rs.getInt("BookingNo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				dbConnect.closeDatabaseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ++lastNo;
	}
}
