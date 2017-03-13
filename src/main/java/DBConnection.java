import java.io.FileInputStream;



import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.jdbc.MysqlDataSource;

import spark.utils.IOUtils;

public class DBConnection {

	Connection conn = null;
	MysqlDataSource dataSource = null;
	Statement stmt = null;

	public DBConnection() {
		String userName = "";
		String password = "";
		 try(FileInputStream inputStream = new FileInputStream("dbPass.txt"))
		 {
		 String fullFile = IOUtils.toString(inputStream);
		 System.out.println("read my file! " + fullFile);
		
		 String[] fullArray = fullFile.split(",");
		
		 userName = fullArray[0];
		 System.out.println(userName);
		 password = fullArray[1];
		 System.out.println(password);
		
		 } catch(IOException ex){
		 System.out.println("file read error, username: " + ex.getMessage());
		 }

		System.out.println("attempt connection");
		dataSource = new MysqlDataSource();
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		dataSource.setServerName("127.0.0.1");
		dataSource.setPort(3306);
		dataSource.setDatabaseName("bblfh");

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			System.out.println("Connection was complete!");
		} catch (SQLException ex) {
			System.out.println("SQL ERROR: " + ex.getMessage());
		}
	}

	public String getBandName(String id) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT band_name FROM band where band_id =" + id);
			if (rs.first()) {
				conn.close();
				return rs.getString(1);
			} else {
				conn.close();
				return "none found..";
			}
		} catch (SQLException ex) {
			System.out.println("error... " + ex.getMessage());
		}
		return "ERRORRRR";
	}

	public ArrayList<String> getBandNames() {
		ArrayList<String> myArray = new ArrayList<String>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM band");
			System.out.println("Success!!!");
			if (rs.first()) {
				System.out.println("moved to first!");
				myArray.add(rs.getString(2));
				System.out.println("1 test: " + rs.getString(2));
				while (rs.next()) {
					myArray.add(rs.getString(2));
					System.out.println("1 test: " + rs.getString(2));
				}
			} else {
				System.out.println("didn't get anything back...");
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return myArray;
	}

	public String getBands() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM band");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allBands = new JSONArray();
				try {
					JSONObject singleBand = new JSONObject();
					singleBand.put("bandID", rs.getInt(1));
					singleBand.put("bandName", rs.getString(2));
					singleBand.put("bandGenre", rs.getString(3));
					allBands.put(singleBand);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleBand = new JSONObject();
						singleBand.put("bandID", rs.getInt(1));
						singleBand.put("bandName", rs.getString(2));
						singleBand.put("bandGenre", rs.getString(3));
						allBands.put(singleBand);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					fullOb.put("bands", allBands);
					return String.valueOf(fullOb);
				} catch (JSONException ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				System.out.println("didn't get anything back...");
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		return "failure...";
	}
	public String getTours() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour;");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allTours = new JSONArray();
				try {
					JSONObject singleTour = new JSONObject();
					singleTour.put("tourID", rs.getInt(1));
					singleTour.put("bandID", rs.getInt(2));
					singleTour.put("tourStart", rs.getString(3));
					singleTour.put("tourEnd", rs.getString(4));
					allTours.put(singleTour);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleTour = new JSONObject();
						singleTour.put("tourID", rs.getInt(1));
						singleTour.put("bandID", rs.getInt(2));
						singleTour.put("tourStart", rs.getString(3));
						singleTour.put("tourEnd", rs.getString(4));
						allTours.put(singleTour);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					fullOb.put("tours", allTours);
					return String.valueOf(fullOb);
				} catch (JSONException ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				System.out.println("didn't get anything back...");
			}
			
			conn.close();
		} catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return "failure...";
	}
	public String getEventByID(int tourID) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour_date where tour_id = " + String.valueOf(tourID));
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allEvents = new JSONArray();
				try {
					JSONObject singleEvent = new JSONObject();
					singleEvent.put("tdID", rs.getInt(1));
					singleEvent.put("tourID", rs.getInt(2));
					singleEvent.put("tdDate", rs.getString(3));
					singleEvent.put("tdName", rs.getString(4));
					singleEvent.put("tdAddress", rs.getString(5));
					singleEvent.put("tdLat", rs.getFloat(6));
					singleEvent.put("tdLng", rs.getFloat(7));
					singleEvent.put("tdType", rs.getInt(8));
					singleEvent.put("tdHomeConfirmed", rs.getInt(9));
					allEvents.put(singleEvent);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleEvent = new JSONObject();
						singleEvent.put("tdID", rs.getInt(1));
						singleEvent.put("tourID", rs.getInt(2));
						singleEvent.put("tdDate", rs.getString(3));
						singleEvent.put("tdName", rs.getString(4));
						singleEvent.put("tdAddress", rs.getString(5));
						singleEvent.put("tdLat", rs.getFloat(6));
						singleEvent.put("tdLng", rs.getFloat(7));
						singleEvent.put("tdType", rs.getInt(8));
						singleEvent.put("tdHomeConfirmed", rs.getInt(9));
						allEvents.put(singleEvent);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					fullOb.put("tourDates", allEvents);
					return String.valueOf(fullOb);
				} catch (JSONException ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				System.out.println("didn't get anything back...");
			}
			
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return "failure.....";
	}

	public String addBand(String name, String genre) {
		try {
			stmt.execute("INSERT INTO band (band_name,band_genre) values('" + name + "','" + genre + "');");
			return "Success!";
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "failure...";
	}

	public String addTour(int bandID, String startDate, String endDate) {
		try {
			stmt.execute("INSERT INTO tour (band_id,start_date,end_date) values('" + String.valueOf(bandID) + "','"
					+ startDate + "','" + endDate + "');");
			conn.close();
			return "Success!";
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "failure...";
	}

	public String addEvent(int tourID, String tourDate, String tourName, String tourAddress, Float lat, Float lng,
			int type, int houseConfirmed) {

		try {
			stmt.execute(
					"INSERT INTO tour_date (tour_id,td_date,td_name,td_address,td_lat,td_lng,td_type,home_confirmed) VALUES('"
							+ String.valueOf(tourID) + "','" + tourDate + "','" + tourName + "','" + tourAddress + "','"
							+ String.valueOf(lat) + "','" + String.valueOf(lng) + "','" + String.valueOf(type) + "','"
							+ String.valueOf(houseConfirmed) + "');");
			conn.close();
			return "Success!";
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "failure...";
	}
}

