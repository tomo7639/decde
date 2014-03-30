package DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GPS.Place;
import GPS.Town;

public class TownReader {

	private Connection cnn;
	
	public TownReader(){
		try {
			cnn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS"); 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public ArrayList<Town> getTownInTheNear(Place place, double tolerance){
		
		ResultSet townsAcc=null;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = 	" LAT >"+     (place.getLat()-tolerance) +
     		   				" AND LAT <"+ (place.getLat()+tolerance) +
     		   				" AN"
     		   				+ "D LNG >"+ (place.getLng()-tolerance) +
     		   				" AND LNG <"+ (place.getLng()+tolerance) +";";
			townsAcc = stmt.executeQuery( "SELECT * FROM cities WHERE "+statement );	
							
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try {
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("town"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reslt;
	}
	
	public ArrayList<Town> getAllDestTowns(){
		ResultSet townsAcc;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = "SELECT DISTINCT c.* from route r"
					+ "			JOIN(SELECT * FROM route_town "
					+ "			WHERE id IN (SELECT max(id) FROM route_town"
					+ "				GROUP BY route_id)) dest ON dest.route_id = r.rt_id"
					+ "			JOIN cities c ON dest.town_id = c.id";
			townsAcc = stmt.executeQuery( statement );
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("town"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reslt;
	}
	
	public ArrayList<Town> getAllBaseTowns(){
		ResultSet townsAcc;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = "SELECT DISTINCT c.* from route r"
					+ "			JOIN(SELECT * FROM route_town "
					+ "			WHERE id IN (SELECT min(id) FROM route_town"
					+ "				GROUP BY route_id)) base ON base.route_id = r.rt_id"
					+ "			JOIN cities c ON base.town_id = c.id";
			townsAcc = stmt.executeQuery( statement );
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("town"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reslt;
	}
	
	public void closeCnn(){
		try {
			cnn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
