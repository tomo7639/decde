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
			townsAcc = stmt.executeQuery( "SELECT * FROM town WHERE "+statement );	
							
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try {
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("name"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reslt;
	}
	
	public ArrayList<Town> getAllRouteTowns(int routeID){
		ResultSet townsAcc;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = 	"SELECT t.* FROM town t "+
								"JOIN route_town rt ON t.id = rt.town_id "+
								"WHERE rt.route_id = " + routeID + " "+
								"ORDER BY rt.id";
			townsAcc = stmt.executeQuery( statement );
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("name"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reslt;
	}
	
	public ArrayList<Town> getAllDestTowns(){
		ResultSet townsAcc;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = "SELECT DISTINCT t.* from route r"
					+ "			JOIN(SELECT * FROM route_town "
					+ "			WHERE id IN (SELECT max(id) FROM route_town"
					+ "				GROUP BY route_id)) dest ON dest.route_id = r.id"
					+ "			JOIN town t ON dest.town_id = t.id";
			townsAcc = stmt.executeQuery( statement );
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("name"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
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
			String statement = "SELECT DISTINCT t.* FROM route r"
					+ "			JOIN(SELECT * FROM route_town "
					+ "			WHERE id IN (SELECT min(id) FROM route_town"
					+ "				GROUP BY route_id)) base ON base.route_id = r.id"
					+ "			JOIN town t ON base.town_id = t.id";
			townsAcc = stmt.executeQuery( statement );
			while(townsAcc.next()){
				reslt.add(new Town(townsAcc.getString("name"), townsAcc.getFloat("lat"), townsAcc.getFloat("lng"), townsAcc.getInt("id")) );
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
