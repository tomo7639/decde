package DBMS.Archive;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArchiveSaver {

	private Connection cnn;
	
	public ArchiveSaver(Connection con){
		this.cnn = con;
	}
	
	/**
	 * Presunie vsetky zaznamy empl-route s ID v parametri do archivu. Presunie aj zaznamy route a employee, ak je treba
	 * Vycisti databazu od nepouzivanych employee a route, route-town
	 * @param rtEmpIDs
	 */
	public void sendToArchive(ArrayList<Integer> rtEmpIDs){
		Statement stmt;
		try{		
			for (int rtEmpID : rtEmpIDs){
				stmt = cnn.createStatement();
				String sql = "INSERT INTO arch_route "+
							"SELECT * from route "+
							"WHERE id IN "+
							"	(SELECT route_id FROM route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM arch_route); "+

							"INSERT INTO arch_route_town "+
							"SELECT * from route_town "+
							"WHERE route_id IN "+
							"	(SELECT route_id FROM route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM arch_route_town); "+

							"INSERT INTO arch_employee "+
							"SELECT * from employee "+
							"WHERE id IN "+ 
							"	(SELECT employee_id FROM route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM arch_employee); "+

							"INSERT INTO arch_route_employee "+
							"SELECT * from route_employee "+
							"WHERE id = "+rtEmpID+"; "+

							"DELETE FROM route_employee "+
							"WHERE id = "+rtEmpID+"; ";			
				stmt.executeUpdate(sql);
			}			
			cleanUp();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Vycisi DB od nepouzivanych- nereferencovanych employee a route, route-town
	 */
	public void cleanUp(){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"DELETE FROM employee "+
							"WHERE ID NOT IN "+
							"	(SELECT employee_id FROM route_employee); "+
							
							"DELETE FROM route_town "+
							"WHERE route_id NOT IN "+
							"	(SELECT id FROM route "+
							"	WHERE id IN "+
							"		(SELECT route_id FROM route_employee)); "+
							
							"DELETE FROM route "+
							"WHERE id NOT IN "+
							"	(SELECT route_id FROM route_employee);";			
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Presunie vsetky zaznamy empl-route s ID v parametri z archivu. Presunie aj zaznamy route a employee, ak je treba
	 * Vycisti archiv od nepouzivanych employee a route, route-town
	 * @param rtEmpIDs
	 */
	public void bringFromArchive(ArrayList<Integer> rtEmpIDs){
		Statement stmt;
		try{		
			for (int rtEmpID : rtEmpIDs){
				stmt = cnn.createStatement();
				String sql = "INSERT INTO route "+
							"SELECT * from arch_route "+
							"WHERE id IN "+
							"	(SELECT route_id FROM arch_route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM route); "+

							"INSERT INTO route_town "+
							"SELECT * from arch_route_town "+
							"WHERE route_id IN "+
							"	(SELECT route_id FROM arch_route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM route_town); "+

							"INSERT INTO employee "+
							"SELECT * from arch_employee "+
							"WHERE id IN "+ 
							"	(SELECT employee_id FROM arch_route_employee re "+
							"	WHERE re.id = "+rtEmpID+") "+
							"AND id NOT IN "+
							"	(SELECT id FROM employee); "+

							"INSERT INTO route_employee "+
							"SELECT * from arch_route_employee "+
							"WHERE id = "+rtEmpID+"; "+

							"DELETE FROM arch_route_employee "+
							"WHERE id = "+rtEmpID+"; ";			
				stmt.executeUpdate(sql);
			}			
			cleanUpArch();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Vycisi archiv od nepouzivanych- nereferencovanych employee a route, route-town
	 */
	public void cleanUpArch(){
		Statement stmt;
		try{
			stmt = cnn.createStatement();
			String sql = 	"DELETE FROM arch_employee "+
							"WHERE ID NOT IN "+
							"	(SELECT employee_id FROM arch_route_employee); "+
							
							"DELETE FROM arch_route_town "+
							"WHERE route_id NOT IN "+
							"	(SELECT id FROM arch_route "+
							"	WHERE id IN "+
							"		(SELECT route_id FROM arch_route_employee)); "+
							
							"DELETE FROM arch_route "+
							"WHERE id NOT IN "+
							"	(SELECT route_id FROM arch_route_employee);";			
			stmt.executeUpdate(sql);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
}
