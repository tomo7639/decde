package DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GPS.Employee;

public class EmplReader {
	
	Connection cnn;
		
	public EmplReader(){
		try {
			cnn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS"); 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * vráti ArrayList<Employee> s menami vsetkych zamestnanov
	 * 1. ID v DB
	 * 2. Meno
	 * 3. Priezvisko
	 * 4. Titul
	 **/
	public ArrayList<Employee> getAllEmpNames(){
		ArrayList<Employee> reslt = new ArrayList<Employee>();
		try {
			Statement stmt = cnn.createStatement();
			ResultSet emplAcc = stmt.executeQuery( "SELECT * FROM Employee");
			
			while(emplAcc.next()){
				String auxR[] = new String[4];
				int id=emplAcc.getInt(1);
				auxR[1]=emplAcc.getString(2);
				auxR[2]=emplAcc.getString(3);
				auxR[3]=emplAcc.getString(4);
				reslt.add(new Employee(id, auxR[1], auxR[2], auxR[3]));
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return reslt;
	}
	
	public ArrayList<Employee> getEmpNamesWithinRoute(int routeID){
		ArrayList<Employee> reslt = new ArrayList<Employee>();
		try {
			Statement stmt = cnn.createStatement();
			String statement = 	"SELECT e.* FROM employee e "+
								"JOIN route_employee re ON e.id = re.employee_id "+
								"WHERE re.route_id = " +routeID;
			ResultSet emplAcc = stmt.executeQuery( statement );
			
			while(emplAcc.next()){
				String auxR[] = new String[4];
				int id=emplAcc.getInt(1);
				auxR[1]=emplAcc.getString(2);
				auxR[2]=emplAcc.getString(3);
				auxR[3]=emplAcc.getString(4);
				reslt.add(new Employee(id, auxR[1], auxR[2], auxR[3]));
			}			
		} catch (Exception e) {
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
