package DBMS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ArchiveSaver {

	private Connection cnn;
	
	public ArchiveSaver(Connection con){
		this.cnn = con;
	}
	
	public void sendToArchive(int routeID){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"INSERT INTO arch_route "+
							"SELECT * from route "+
							"WHERE id = "+routeID+"; "+

							"INSERT INTO arch_route_town "+
							"SELECT * from route_town "+
							"WHERE route_id = "+routeID+" "+

							"INSERT INTO arch_employee "+
							"SELECT * from employee "+
							"WHERE id IN "+ 
							"	(SELECT employee_id FROM route_employee "+
							"	WHERE route_id = "+routeID+");"+

							"INSERT INTO arch_route_employee "+
							"SELECT * from route_employee "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM route_employee "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM route_town "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM route "+
							"WHERE id = "+routeID+";";
			
			stmt.executeUpdate(sql);
			
			checkEmpConsis();
		}
		catch (SQLException e){
		}
	}
	
	public void bringFromArchive(int routeID){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"INSERT INTO route "+
							"SELECT * from arch_route "+
							"WHERE id = 2; "+

							"INSERT INTO route_town "+
							"SELECT * from arch_route_town "+
							"WHERE route_id = "+routeID+" "+

							"INSERT INTO employee "+
							"SELECT * from arch_employee "+
							"WHERE id IN "+ 
							"	(SELECT employee_id FROM arch_route_employee "+
							"	WHERE route_id = "+routeID+");"+

							"INSERT INTO route_employee "+
							"SELECT * from arch_route_employee "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM arch_route_employee "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM arch_route_town "+
							"WHERE route_id = "+routeID+";"+

							"DELETE FROM arch_route "+
							"WHERE id = "+routeID+";";
			
			stmt.executeUpdate(sql);
			
			checkEmpConsisInArch();			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void checkEmpConsis(){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"DELETE FROM employee "+
							"WHERE ID NOT IN "+
							"	(SELECT employee_id FROM route_employee);";			
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void checkEmpConsisInArch(){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"DELETE FROM arch_employee "+
							"WHERE ID NOT IN "+
							"	(SELECT employee_id FROM arch_route_employee);";			
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
}
