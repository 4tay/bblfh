import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ThreadPoolExecutor; 
public class Main {
	public static void main(String[] args) {
		
		
		
		get("/bands", (req, res) -> {
			DBConnection conn = new DBConnection();
			return conn.getBands();
		});
		 
		get("/bandNames", (req, res) -> {
			DBConnection conn = new DBConnection();
			ArrayList<String> bandNames = conn.getBandNames();

			JSONObject fullOb = new JSONObject();
			JSONArray allBands = new JSONArray();
			for (int i = 0; i < bandNames.size(); i++) {
				try {
					JSONObject singleBand = new JSONObject();
					singleBand.put("bandName", bandNames.get(i));
					allBands.put(singleBand);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
			}
			try {
				fullOb.put("bands", allBands);
			} catch (JSONException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("This happened");
			return fullOb;
		});
		get("/fan/:fanID", (req,res) -> {
			
			DBConnection conn = new DBConnection();
			return conn.getFan(Integer.parseInt(req.params(":fanID")));
			
		});
		
		get("/fans", (req,res) -> {
			DBConnection conn = new DBConnection();
			return conn.getAllFans();
		});
		get("/band/:bandID", (req, res) -> {
			DBConnection conn = new DBConnection();
			return conn.getBandName(Integer.parseInt(req.params(":bandID")));
		});
		get("/tours", (req, res) -> {
			DBConnection conn = new DBConnection();
			return conn.getTours();
		});
		get("/eventsByID/:tourID", (req,res) -> {
			DBConnection conn = new DBConnection();
			return conn.getEventByID(Integer.parseInt(req.params(":tourID")));
		});

		post("/addBand", (req, res) -> {
			DBConnection conn = new DBConnection();
			String bandName = req.queryParams("bandName");
			String bandGenre = req.queryParams("bandGenre");
			String response = conn.addBand(bandName, bandGenre);
			System.out.println(bandName);

			return response;
		});
		post("/addTour", (req,res) -> {
			DBConnection conn = new DBConnection();
			return conn.addTour(
					req.queryParams("tourName"), 
					req.queryParams("tourStart"), 
					req.queryParams("tourEnd"));
		});
		post("/addEvent", (req,res) -> {
			DBConnection conn = new DBConnection();
			
			System.out.println("got a connection");
			
			/*TODO
			 * FIX THIS addEvent doesn't currently work...
			 */
			
			return conn.addEvent(Integer.parseInt(req.queryParams("tourID")), 
					req.queryParams("showDate"), req.queryParams("showName"), 
					req.queryParams("showAddress"), req.queryParams("showAddressTwo"),
					Integer.parseInt(req.queryParams("showZip")), req.queryParams("showCity"),
					req.queryParams("showState"), req.queryParams("showCountry"),
					Integer.parseInt(req.queryParams("showType")), 
					Integer.parseInt(req.queryParams("homeConfirmed")),
					Float.parseFloat(req.queryParams("lat")),
					Float.parseFloat(req.queryParams("lng")));
			
		});
		
		post("/addFan", (req,res) -> {
			DBConnection conn = new DBConnection();
			
			return conn.addFan(req.queryParams("fanName"),
					req.queryParams(req.queryParams("fanEmail")));
		});

	}
	
	
}
