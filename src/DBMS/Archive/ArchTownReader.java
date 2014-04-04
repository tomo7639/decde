package DBMS.Archive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GPS.Town;

public class ArchTownReader {
private Connection cnn;
	
	public ArchTownReader(){
		try {
			cnn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS"); 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public ArrayList<Town> getAllDestTowns(){
		ResultSet townsAcc;
		ArrayList<Town> reslt = new ArrayList<Town>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = "SELECT DISTINCT t.* from arch_route r"
					+ "			JOIN(SELECT * FROM arch_route_town "
					+ "			WHERE id IN (SELECT max(id) FROM arch_route_town"
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
			String statement = "SELECT DISTINCT t.* FROM arch_route r"
					+ "			JOIN(SELECT * FROM arch_route_town "
					+ "			WHERE id IN (SELECT min(id) FROM arch_route_town"
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
