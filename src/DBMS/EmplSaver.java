package DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import GPS.Employee;

public class EmplSaver {

	private static int PKID;
	private Connection cnn;
	
	public EmplSaver(Connection con){
		try {
			this.cnn = con;
			Statement max = cnn.createStatement();
			ResultSet maxAcc = max.executeQuery( "SELECT emp_id FROM employees "
					+ "									ORDER BY emp_id DESC"
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
	
	public void save(Employee em){
		Statement stmt;
		try {
			stmt = cnn.createStatement();
			String sql = "INSERT INTO employees " +
	                   "VALUES ("+em.getID()+",'"+em.getFName()+"','"+em.getSName()+"','"+em.getDName()+"')";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	      
	}
	
	public int getAutoIncrPK(){
		return ++PKID;
	}
	
}
