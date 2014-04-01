package DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RtTwSaver {

	private static int PKID;
	private Connection cnn;
	
	public RtTwSaver(Connection con){
		try {
			this.cnn = con;
			Statement max = cnn.createStatement();
			ResultSet maxAcc = max.executeQuery( "SELECT id FROM route_town "
					+ "									ORDER BY id DESC"
					+ "									LIMIT 1");	
			if(	maxAcc.next()){
				PKID = maxAcc.getInt("id");
			}
			else{
				PKID = 0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void save(int ID, int rtID, int twID){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "INSERT INTO route_town " +
	                   "VALUES ("+twID+","+rtID+","+ID+")";
			stmt.executeUpdate(sql);
		}
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	      
	}
	
	public void deleteRow(int routeID){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "DELETE FROM route_town "+
						 "WHERE route_id = "+routeID;
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getAutoIncrPK(){
		return ++PKID;
	}

}