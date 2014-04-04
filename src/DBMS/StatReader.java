package DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GPS.DandTime;

public class StatReader {

	private Connection cnn;
	
	public StatReader(){
		try {
			cnn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS"); 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void read (JTable t1, DandTime from, DandTime to){
		ResultSet Acc=null;
		
		try {
			Statement stmt = cnn.createStatement();
			String statement = 	"SELECT e.id, e.emp_fname, e.emp_sname, e.emp_dname, sum(r.kms) FROM employee e "+
								"JOIN route_employee re ON re.employee_id = e.id "+
								"JOIN route r ON r.id = re.route_id "+
								"WHERE 1 = 1 ";
			if (!from.isNull()){
				statement += 	"AND r.rstart > '"+from.getDT()+"' ";
			}
			if (!to.isNull()){
				statement += 	"AND r.rend < '"+to.getDT()+"' ";
			}
			
			statement +=		"GROUP BY e.id "+
								"ORDER BY sum(r.kms) DESC ";					
					
			Acc = stmt.executeQuery( statement );	
							
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try {
			while(Acc.next()){
				((DefaultTableModel)t1.getModel()).addRow(new Object[]{Acc.getInt(1), Acc.getString(2)+" "+Acc.getString(3)+" "+Acc.getString(4), Acc.getFloat(5)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
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
