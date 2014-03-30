package DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import GPS.Employee;

public class EmplReader {
	
	/**
	 * DEBUG ONLY
	 * @param args
	 */
	public static void main(String[] args){
		ArrayList<Employee> l = getAllEmpNames();
		for(Employee s: l){
			System.out.println(s.getID()+" "+s.getFName()+" "+s.getSName()+" "+s.getDName()+" ");
		}
	}
	
	/**
	 * vráti ArrayList<Employee> s menami vsetkych zamestnanov
	 * 1. ID
	 * 2. Meno
	 * 3. Priezvisko
	 * 4. Titul
	 **/
	public static ArrayList<Employee> getAllEmpNames(){
		ArrayList<Employee> reslt = new ArrayList<Employee>();
		try {
			Connection empDataFlow = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS");
			Statement stmt = empDataFlow.createStatement();
			ResultSet emplAcc = stmt.executeQuery( "SELECT * FROM Employees");
			
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
		} finally {
			//empDataFlow.close();
		}
		
		return reslt;
	}
}
