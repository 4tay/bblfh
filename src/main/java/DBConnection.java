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
		 try(FileInputStream inputStream = new FileInputStream("dbPass.txt")) {
			 String fullFile = IOUtils.toString(inputStream);
			 System.out.println("read my file! " + fullFile);
			 String[] fullArray = fullFile.split(",");
			 userName = fullArray[0];
			 System.out.println(userName);
			 password = fullArray[1];
			 System.out.println(password);
		 }
		 catch(IOException ex){
			 System.out.println("file read error, username: " + ex.getMessage());
		 }

		System.out.println("attempt connection");
		dataSource = new MysqlDataSource();
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		dataSource.setServerName("127.0.0.1");
//		dataSource.setUser("businessFawn");
//		dataSource.setPassword("D#Wg0ng");
//		dataSource.setServerName("192.168.1.98");
		dataSource.setPort(3306);
		dataSource.setDatabaseName("harbor");

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			System.out.println("Connection was complete!");
		} catch (SQLException ex) {
			System.out.println("SQL ERROR: " + ex.getMessage());
		}
	}

	public String getBandName(int bandID) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT band_name FROM band where band_id = '" + String.valueOf(bandID) + "';");
			if (rs.first()) {
				String bandName = rs.getString(1);
				conn.close();
				return bandName;
			} else {
				conn.close();
				return "none found..";
			}
		} catch (SQLException ex) {
			System.out.println("error... " + ex.getMessage());
		}
		return "ERRORRRR";
	}
	
	public String getFan(int fanID) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM fan where fan_id = '" + String.valueOf(fanID) + "';");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allFans = new JSONArray();
				try {
					JSONObject singleFan = new JSONObject();
					singleFan.put("fanID", rs.getInt(1));
					singleFan.put("fanName", rs.getString(2));
					singleFan.put("fanEmail", rs.getString(3));
					singleFan.put("fanRank", rs.getInt(4));
					allFans.put(singleFan);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleFan = new JSONObject();
						singleFan.put("fanID", rs.getInt(1));
						singleFan.put("fanName", rs.getString(2));
						singleFan.put("fanEmail", rs.getString(3));
						singleFan.put("fanRank", rs.getInt(4));
						allFans.put(singleFan);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					fullOb.put("fans", allFans);
					conn.close();
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
	
	public String getAllFans() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM fan");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allFans = new JSONArray();
				try {
					JSONObject singleFan = new JSONObject();
					singleFan.put("fanID", rs.getInt(1));
					singleFan.put("fanName", rs.getString(2));
					singleFan.put("fanEmail", rs.getString(3));
					singleFan.put("fanRank", rs.getInt(4));
					allFans.put(singleFan);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleFan = new JSONObject();
						singleFan.put("fanID", rs.getInt(1));
						singleFan.put("fanName", rs.getString(2));
						singleFan.put("fanEmail", rs.getString(3));
						singleFan.put("fanRank", rs.getInt(4));
						allFans.put(singleFan);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					fullOb.put("fans", allFans);
					conn.close();
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
		return "failure....";
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour");
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
	public String getLogin(String userName, String password) {
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM fan WHERE fan_email = '" + userName + "' and fan_pass = '" + password + "';");
			System.out.println("Looking for a fan....");
			if(rs.first()) {
				System.out.println("Got a fan!!!");
				JSONObject fullOb = new JSONObject();
				try {
					fullOb.put("fanID",rs.getInt(1));
					fullOb.put("fanName", rs.getString(2));
					fullOb.put("fanEmail", rs.getString(3));
					fullOb.put("fanAchievement", rs.getString(4));
					
					return fullOb.toString();
				} catch(JSONException e) {
					System.out.println("JSON Error.... " + e.getMessage());
				}
			}
			
		} catch(SQLException e) {
			System.out.println("Error.... " + e.getMessage());
		}
		
		
		return"failure....";
	}
	public String getEventByID(int tourID) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour_show where tour_id = '" + String.valueOf(tourID) + "';");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allEvents = new JSONArray();
				try {
					JSONObject singleEvent = new JSONObject();
					singleEvent.put("showID", rs.getInt(1));
					singleEvent.put("tourID", rs.getInt(2));
					singleEvent.put("showDate", rs.getString(3));
					singleEvent.put("showName", rs.getString(4));
					singleEvent.put("showAddress", rs.getString(5));
					singleEvent.put("showAddressTwo", rs.getString(6));
					singleEvent.put("showZip", rs.getInt(7));
					singleEvent.put("showCity", rs.getString(8));
					singleEvent.put("showState", rs.getString(9));
					singleEvent.put("showCountry", rs.getString(10));
					singleEvent.put("showType", rs.getInt(11));
					singleEvent.put("homeConfirmed", rs.getInt(12));
					singleEvent.put("showLat", rs.getFloat(13));
					singleEvent.put("showLng", rs.getFloat(14));
					allEvents.put(singleEvent);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("moved to first!");
				while (rs.next()) {
					try {
						JSONObject singleEvent = new JSONObject();
						singleEvent.put("showID", rs.getInt(1));
						singleEvent.put("tourID", rs.getInt(2));
						singleEvent.put("showDate", rs.getString(3));
						singleEvent.put("showName", rs.getString(4));
						singleEvent.put("showAddress", rs.getString(5));
						singleEvent.put("showAddressTwo", rs.getString(6));
						singleEvent.put("showZip", rs.getInt(7));
						singleEvent.put("showCity", rs.getString(8));
						singleEvent.put("showState", rs.getString(9));
						singleEvent.put("showCountry", rs.getString(10));
						singleEvent.put("showType", rs.getInt(11));
						singleEvent.put("homeConfirmed", rs.getInt(12));
						singleEvent.put("showLat", rs.getFloat(13));
						singleEvent.put("showLng", rs.getFloat(14));
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

	public String addTour(String tourName, String startDate, String endDate) {
		try {
			stmt.execute("INSERT INTO tour (tour_name,start_date,end_date) values('" + tourName + "','"
					+ startDate + "','" + endDate + "');");
			conn.close();
			return "Success!";
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "failure...";
	}

	public String addEvent(int tourID, String showDate,
			String showName, String showAddress, String showAddressTwo,
			int showZip, String showCity, String showState, String showCountry,
			int showType, int homeConfirmed, Float lat, Float lng) {

		try {
			stmt.execute(
					"INSERT INTO tour_show (tour_id,show_date,show_name,show_address, show_address_2, show_zip, "
					+ "show_city, show_state, show_country, show_type, home_confirmed,show_lat,show_lng) " +
					"VALUES('"+ String.valueOf(tourID) + "','" + showDate + "','" + showName + "','" + showAddress
					+ "','" + showAddressTwo + "','" + showZip + "','" + showCity + "','" + showState + "','"
					+ showCountry + "','" + String.valueOf(showType) + "','" + String.valueOf(homeConfirmed) + "','"
					+ String.valueOf(lat) + "','" + String.valueOf(lng) + "');");
			conn.close();
			return "Success!";
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "failure...";
	}
	
	public String addFan(String fanName, String fanEmail, String fanPass) {
		try {
			stmt.execute("INSERT INTO fan (fan_name,fan_email,fan_pass) values('"+fanName +"','" + fanEmail+"','" + fanPass + "');");
			conn.close();
			return "Success!";
		}
		catch (SQLException ex) {
		System.out.println(ex.getMessage());
		}
		
		return "failure...";
	}
	
}