package DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RtEmSaver {

	private static int PKID;
	private Connection cnn;
	
	public RtEmSaver(Connection con){
		try {
			this.cnn = con;			
			Statement max = cnn.createStatement();
			ResultSet maxAcc = max.executeQuery( "SELECT id FROM route_employee "
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
	
	public void deleteRow(int routeID){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "DELETE FROM route_employee "+
						 "WHERE route_id = "+routeID;
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void save(int ID, int emID, int rtID){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "INSERT INTO route_employee " +
	                   "VALUES ("+ID+","+emID+","+rtID+")";
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