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

			System.out.println("This hapened");
			return fullOb;
		});
		get("/band/:bandID", (req, res) -> {
			DBConnection conn = new DBConnection();

			String band = conn.getBandName(req.params(":bandID"));

			return band;
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
			return conn.addTour(Integer.parseInt(req.queryParams("bandID")), 
					req.queryParams("tourStart"), 
					req.queryParams("tourEnd"));
		});
		post("/addEvent", (req,res) -> {
			DBConnection conn = new DBConnection();
			return conn.addEvent(Integer.parseInt(req.queryParams("tourID")), 
					req.queryParams("gigDate"), req.queryParams("tourName"), 
					req.queryParams("tourAddress"), Float.parseFloat(req.queryParams("lat")), 
					Float.parseFloat(req.queryParams("lng")), Integer.parseInt(req.queryParams("type")),
					Integer.parseInt(req.queryParams("houseConfirmed")));
		});

	}
	
	
}
