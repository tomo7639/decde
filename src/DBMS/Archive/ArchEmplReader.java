package DBMS.Archive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GPS.Employee;

public class ArchEmplReader {
	Connection cnn;
	
	public ArchEmplReader(){
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
			ResultSet emplAcc = stmt.executeQuery( "SELECT * FROM arch_employee");
			
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
