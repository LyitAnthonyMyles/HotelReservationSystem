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

	//happy with this
	public void addBooking(int bookingNo, String checkInDate, String checkOutDate, int custNo, int roomNo) {
		conn = dbConnect.connectToDatabase();
		String sql = "INSERT INTO booking VALUES (" + bookingNo + ", '" + checkInDate  + "', '" +  checkOutDate +  "', "  
													+ custNo + ", " + roomNo + ", false, false)";

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Booking ADDED!!!");
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
	}

	//happy with this
	public void checkIn(String no) {
		conn = dbConnect.connectToDatabase();
		String sql = "UPDATE booking SET CheckIn = true WHERE BookingNo = " + Integer.parseInt(no);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Checked in the booking!!!");
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
	}

	//happy with this
	public ArrayList<String> getBookingAvailability(LocalDate checkDate, LocalDate checkoutDate, String type) {	
		conn = dbConnect.connectToDatabase();
		ArrayList<Integer> unavailableNums = new ArrayList<>();
		ArrayList<String> availability = new ArrayList<>();

		//SELECT * from booking WHERE (CheckInDate NOT BETWEEN '2022-05-07' AND '2022-05-10') 
		//				AND (CheckOutDate NOT BETWEEN '2022-05-07' AND '2022-05-10');
		//ISSUE HERE NEED TO FIX THIS! DOES NOT RETURN CORRECT UNAVAILABLE ROOMS
		String sqlUnavailable = "SELECT * from booking WHERE (CheckInDate BETWEEN '" + checkDate.toString() 
								+ "' AND '" + checkoutDate.toString() + "') OR (CheckOutDate BETWEEN '" 
								+ checkDate.toString() + "' AND '" + checkoutDate.toString() + "')";
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlUnavailable);
			while(rs.next()) {
				unavailableNums.add(rs.getInt("RoomNo"));
			}
			String sqlAvailable = "SELECT * FROM room WHERE RoomType= '" + type + "' AND Decommissioned = false";
			rs = stmt.executeQuery(sqlAvailable);
			while (rs.next()) {
				availability.add("Room: " + rs.getInt("RoomNo") + " Room Type: " + rs.getString("RoomType"));
			}

			if (unavailableNums.size() > 0) {
				for (int no : unavailableNums) {
					int counter = 0;
					while (counter < availability.size()) {
						if (availability.get(counter).contains(" " + no + " ")) {
							availability.remove(counter);
						} else {
							counter++;
						}
					}
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
		return availability;
	}
	
	//happy with this
	public ArrayList<String> getExistingBookings() {
		ArrayList<String> existingBookings = new ArrayList<>();
		conn = dbConnect.connectToDatabase();
		String sql = "SELECT * FROM booking";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				existingBookings.add("Room No: " + rs.getInt("RoomNo") + ", Booking No: " + rs.getInt("BookingNo"));
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

	//happy with this
	public ArrayList<String> getBookingsToCheckIn() {
		ArrayList<String> existingBookings = new ArrayList<>();
		conn = dbConnect.connectToDatabase();
		String sql = "SELECT * FROM booking WHERE CheckIn = false AND CheckInDate = '" + LocalDate.now() + "'";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				existingBookings.add("Room No: " + rs.getInt("RoomNo") + " , Booking No: " + rs.getInt("BookingNo"));
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

	//fairly happy
	public ArrayList<String> getBookingsToBill() {
		ArrayList<String> bookingsToBill = new ArrayList<>();
		conn = dbConnect.connectToDatabase();

		String sql = "SELECT * FROM booking WHERE CheckIn = true AND CheckOutTotal = 0";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				bookingsToBill.add("Room No: " + rs.getInt("RoomNo") + ", Booking No: " + rs.getInt("BookingNo"));
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
		return bookingsToBill;
	}

	//happy with this
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

	//happy with this
	public String getBookingDetails(int bookingNo) {
		String details = "";
		conn = dbConnect.connectToDatabase();
		String sql = "SELECT * FROM booking WHERE BookingNo=" + bookingNo;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				details += "Booking: " + rs.getString("BookingNo") + ", Room Number: " 
				+ rs.getInt("RoomNo");
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
		return details;
	}
}
