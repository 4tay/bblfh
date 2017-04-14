import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	
	
	public static String makeShowList(ResultSet rs) {
		
		try {
			if (rs.first()) {
			JSONObject fullOb = new JSONObject();
			JSONArray allEvents = new JSONArray();
			try {
				System.out.println("moved to first!");
				do{
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
				} while (rs.next());
				
			} catch (JSONException e) {
				System.out.println(e.getMessage());
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
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return "failure...";
	}
	
	public static String makeHome(ResultSet rs) {
	try {
		if (rs.first()) {
		JSONObject fullOb = new JSONObject();
		JSONArray allHomes = new JSONArray();
		try{ 
			do{
				JSONObject singleHome = new JSONObject();
			singleHome.put("homeID", rs.getInt(1));
			singleHome.put("fanID", rs.getInt(2));
			singleHome.put("homeName", rs.getString(3));
			singleHome.put("homeAddress", rs.getString(4));
			singleHome.put("homeAddressTwo", rs.getString(5));
			singleHome.put("homeZip", rs.getInt(6));
			singleHome.put("homeCity", rs.getString(7));
			singleHome.put("homeState", rs.getString(8));
			singleHome.put("homeCountry", rs.getString(9));
			singleHome.put("homeLat", rs.getFloat(10));
			singleHome.put("homeLng", rs.getFloat(11));
			singleHome.put("homePhoto", rs.getString(12));
			allHomes.put(singleHome);
			} while (rs.next());
			fullOb.put("homes", allHomes);
			return String.valueOf(fullOb);
		}
		catch (JSONException e) {
			System.out.println(e.getMessage());
		}
	} else {
		System.out.println("didn't get anything back...");
	} 
		}
	catch(SQLException e) {
		System.out.println(e.getMessage());
	}
	return "failure...";
	}
	
	
	public static String makeBandList(ResultSet rs) {
		try {
			if (rs.first()) {
			JSONObject fullOb = new JSONObject();
			JSONArray allBands = new JSONArray();
			try{ 
				do{
					JSONObject singleBand = new JSONObject();
				singleBand.put("bandID", rs.getInt(1));
				singleBand.put("bandName", rs.getString(2));
				singleBand.put("bandPhoto", rs.getString(3));
				System.out.println("Building bands.... " + singleBand.toString());
				allBands.put(singleBand);
				} while (rs.next());
				fullOb.put("bands", allBands);
				return String.valueOf(fullOb);
			}
			catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("didn't get anything back...");
		} 
			}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return "failure...";
		}
	
	public static String makeGenreList(ResultSet rs) {
		try {
			if (rs.first()) {
			JSONObject fullOb = new JSONObject();
			JSONArray allGenres = new JSONArray();
			try{ 
				do{
				JSONObject genre = new JSONObject();
				genre.put("genreID", rs.getInt(1));
				genre.put("genreName", rs.getString(2));
				allGenres.put(genre);
				} while (rs.next());
				fullOb.put("genres", allGenres);
				return String.valueOf(fullOb);
			}
			catch (JSONException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("didn't get anything back...");
		} 
			}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return "failure...";
	}

}
