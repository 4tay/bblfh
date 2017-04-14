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
		
		dataSource.setUser("businessFawn");
		dataSource.setPassword("D#Wg0ng");
		dataSource.setServerName("192.168.1.78");
		System.out.println("THISSSSS");
		
		
//		dataSource.setUser(userName);
//		dataSource.setPassword(password);
//		dataSource.setServerName("127.0.0.1");
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
		return "failure....";
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


	public String getBands() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM band");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			if (rs.first()) {
				JSONObject fullOb = new JSONObject();
				JSONArray allBands = new JSONArray();
				do{
					try {
						JSONObject singleBand = new JSONObject();
						singleBand.put("bandID", rs.getInt(1));
						singleBand.put("bandName", rs.getString(2));
						allBands.put(singleBand);
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}
				} while (rs.next());
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
				String eventList = Util.makeShowList(rs);
					conn.close();
					return eventList;
			} else {
				System.out.println("didn't get anything back...");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return "failure.....";
	}
	
	public String getShowsWithinZipRange(int bottom, int top) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour_show where show_zip between '"
		+ String.valueOf(bottom) + "' and '" + String.valueOf(top) + "';");
			String result = Util.makeShowList(rs);
			
			conn.close();
			return result;
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
			return "failure...";
	}
	
	public String getShowsInState(String state) {
		try {
		ResultSet rs = stmt.executeQuery("SELECT * FROM tour_show where show_state = '" + state + "';");
		String result = Util.makeShowList(rs);
		
		conn.close();
		return result;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return "failure...";
	}
	public String getShowsInTown(String city) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour_show where show_city = '" + city + " and show_date > current_date';");
			String result = Util.makeShowList(rs);
			
			conn.close();
			return result;
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
			return "failure...";
	}
	public String showsInTownWithoutConfirmation(String city) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tour_show where show_city = '" + city + "' and home_confirmed ="
					+ " 0 and show_date > current_date;");
			String result = Util.makeShowList(rs);
			
			conn.close();
			return result;
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
			return "failure...";
	}
	public String showsInTownOfHomes(String fanEmail, String fanPassword) {
		
		try {
			try {
			JSONObject userHomes = new JSONObject(getFanHomes(fanEmail,fanPassword));
			System.out.println(userHomes.toString());
			JSONArray homes = userHomes.optJSONArray("homes");
			JSONArray showsCloseToHomes = new JSONArray();
			for (int i = 0; i < homes.length(); i++) {
				JSONObject singleHome = homes.optJSONObject(i);
				DBConnection newConn = new DBConnection();
				JSONObject inTownShows = new JSONObject(newConn.getShowsInTown(singleHome.optString("homeCity")));
				showsCloseToHomes.put(inTownShows);
			}
			
			String showsInTown = showsCloseToHomes.toString();
			conn.close();
			return showsInTown;
			
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println("SQL Error... " + e.getMessage());
		}
		
		
		
		return "failure...";
	}
public String showsInTownWithoutHome(String fanEmail, String fanPassword) {
		
		try {
			try {
			JSONObject userHomes = new JSONObject(getFanHomes(fanEmail,fanPassword));
			System.out.println(userHomes.toString());
			JSONArray homes = userHomes.optJSONArray("homes");
			JSONObject allShows = new JSONObject();
			JSONArray showsCloseToHomes = new JSONArray();
			for (int i = 0; i < homes.length(); i++) {
				JSONObject singleHome = homes.optJSONObject(i);
				DBConnection newConn = new DBConnection();
				System.out.println(singleHome.optString("homeCity"));
				JSONObject inTownShows = new JSONObject(
						newConn.showsInTownWithoutConfirmation(
								singleHome.optString("homeCity")));
				inTownShows.put("homeName",singleHome.opt("homeName"));
				showsCloseToHomes.put(inTownShows);
			}
			allShows.put("shows", showsCloseToHomes);
			String showsInTown = allShows.toString();
			conn.close();
			return showsInTown;
			
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println("SQL Error... " + e.getMessage());
		}
		
		
		
		return "failure...";
	}
	
	public String getFanHomes(String fanEmail, String fanPassword) {
		
		int fanID = -1;
		
		try {
			try {
			JSONObject userOb = new JSONObject(getLogin(fanEmail,fanPassword));
			
			
			fanID = userOb.optInt("fanID");
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM fan_home where fan_id = '" + String.valueOf(fanID) + "';");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			String madeHomes = Util.makeHome(rs);
			conn.close();
			return madeHomes;
			
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println("SQL Error... " + e.getMessage());
		}
		
		
		return "failure....";
	}
	
	public String getFanGenres(String fanEmail, String fanPassword) {
int fanID = -1;
		
		try {
			try {
			JSONObject userOb = new JSONObject(getLogin(fanEmail,fanPassword));
			
			
			fanID = userOb.optInt("fanID");
			
			ResultSet rs = stmt.executeQuery("SELECT genre.genre_id, genre_name FROM fan_to_genre as f2g join "
					+ "genre on f2g.genre_id = genre.genre_id where fan_id = '" + String.valueOf(fanID) + "';");
			System.out.println("Success!");
			System.out.println("This hapened");
			String genreList = Util.makeGenreList(rs);
			conn.close();
			return genreList;
			
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println("SQL Error... " + e.getMessage());
		}
		
		
		return "failure....";
	}
	public String getBandsByGenreID(int genreID) {
				try {
					ResultSet rs = stmt.executeQuery("SELECT band.* FROM band_to_genre as b2g join "
							+ "band on b2g.band_id = band.band_id where genre_id = '" + String.valueOf(genreID) + "';");
					System.out.println("Success!");
					System.out.println("This hapened");
					String genreList = Util.makeBandList(rs);
					conn.close();
					return genreList;
				} catch (SQLException e) {
					System.out.println("SQL Error... " + e.getMessage());
				}
				
				
				return "failure....";
			}
	public String getBandByGenresName(String genreName) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT band.* FROM band_to_genre as b2g join "
					+ "band on b2g.band_id = band.band_id"
					+ "join genre on b2g.genre_id = genre.genre_id "
					+ "where genre_name = '" + genreName + "';");
			System.out.println("Success!!!");
			System.out.println("This hapened");
			String genreList = Util.makeBandList(rs);
			conn.close();
			return genreList;
		} catch (SQLException e) {
			System.out.println("SQL Error... " + e.getMessage());
		}
		
		
		return "failure....";
	}
	
public String fanToBandByGenre(String fanEmail, String fanPassword) {
		
		try {
			JSONObject userGenres = new JSONObject(getFanGenres(fanEmail,fanPassword));
			JSONArray allUserGenres = userGenres.getJSONArray("genres");
			System.out.println(userGenres.toString());
			
			JSONObject fullBandOb = new JSONObject();
			JSONArray allBandGenres = new JSONArray();
			
			for(int i = 0; i < allUserGenres.length(); i++) {
				DBConnection conn2 = new DBConnection();
				JSONObject genre = allUserGenres.getJSONObject(i);
				System.out.println(i + " " + genre.toString());
				JSONObject bandsInGenre = new JSONObject(conn2.getBandsByGenreID(genre.optInt("genreID")));
				System.out.println(i + " " + bandsInGenre.toString());
				bandsInGenre.put("genreName", genre.optString("genreName"));
				System.out.println(i + " " + bandsInGenre.toString());
				allBandGenres.put(bandsInGenre);
					
			}
				fullBandOb.put("allGenresWithBands", allBandGenres);
			
			return fullBandOb.toString();
			
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		
		
		
		return "failure...";
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
	
	public String addFan(String fanName, String fanEmail, String fanPass, String fanPhoto) {
		try {
			stmt.execute("INSERT INTO fan (fan_name,fan_email,fan_pass, fan_photo) values('"+fanName +"','" + fanEmail+"','" + fanPass + "','" + fanPhoto + "');");
			conn.close();
			return "Success!";
		}
		catch (SQLException ex) {
		System.out.println(ex.getMessage());
		}
		
		return "failure...";
	}
	public String addHome(String fanEmail, String fanPassword,
			String homeName, String homeAddress, String homeAddressTwo,
			int homeZip, String homeCity, String homeState, String homeCountry,
			float homeLat, float homeLng, String homePhoto) {
		
		int userID = -1;
		
		try {
			JSONObject fullOb = new JSONObject(getLogin(fanEmail,fanPassword));
			
			userID = fullOb.optInt("fanID");
			
			try {
				System.out.println("INSERT INTO fan_home (fan_id, home_name, home_address, home_address_2,"
						+ "home_zip, home_city, home_state, home_country, home_lat, home_lng, home_photo) "
						+ "VALUES('" + String.valueOf(userID) + "','" + homeName + "','" + homeAddress + "','"
						+ homeAddressTwo + "','" + String.valueOf(homeZip) + "','" + homeCity + "','"
						+ homeState+ "','" + homeCountry + "','" + String.valueOf(homeLat) + "','" + String.valueOf(homeLng)
						+ "','" + homePhoto + "');");
				
				stmt.execute("INSERT INTO fan_home (fan_id, home_name, home_address, home_address_2,"
						+ "home_zip, home_city, home_state, home_country, home_lat, home_lng, home_photo) "
						+ "VALUES('" + String.valueOf(userID) + "','" + homeName + "','" + homeAddress + "','"
						+ homeAddressTwo + "','" + String.valueOf(homeZip) + "','" + homeCity + "','"
						+ homeState+ "','" + homeCountry + "','" + String.valueOf(homeLat) + "','" + String.valueOf(homeLng)
						+ "','" + homePhoto + "');");
				
				return "Success!";
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (JSONException e) {
			
		}
		
		
		return "failure....";
	}
	
}