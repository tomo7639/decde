package DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import GPS.DandTime;
import GPS.Route;

public class RouteSaver {

	private static int PKID;
	private Connection cnn;
	
	public RouteSaver(Connection con){
		try {
			this.cnn = con;
			Statement max = cnn.createStatement();
			ResultSet maxAcc = max.executeQuery( "SELECT rt_id FROM route "
					+ "									ORDER BY rt_id DESC"
					+ "									LIMIT 1");	
			if(	maxAcc.next()){
				PKID = maxAcc.getInt(1);
			}
			else{
				PKID = 0;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void save(Route rt){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "INSERT INTO route " +
	                   "VALUES ("+rt.getID()+","+rt.getKmsLen()+",'"+rt.getStart().getDT()+"','"+rt.getEnd().getDT()+"')";
				stmt.executeUpdate(sql);
		}
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	      
	}
	
	public int getAutoIncrPK(){
		return ++PKID;
	}
		
}
