package DBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AllReader {
	
	private Connection cnn;
		
	public AllReader(){
		try {
			cnn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/ROUTE_DB", "postgres","VAVADBS"); 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void read (JTable t1, Integer empID, Integer dTownID, Integer bTownID){
		
		ResultSet Acc=null;
		
		try {
			Statement stmt = cnn.createStatement();
			String statement = 	"SELECT emp_fname, emp_sname, emp_dname, rstart, rend, t.name, t2.name, kms, r.id FROM route r "+
								"JOIN route_employee re ON r.id = re.route_id "+
								"JOIN employee e ON e.id = re.employee_id "+
								"JOIN(SELECT * FROM route_town "+
								"	WHERE id IN (SELECT max(id) FROM route_town "+
								"		GROUP BY route_id)) dest ON dest.route_id = r.id "+
								"JOIN town t ON dest.town_id = t.id "+
								"JOIN(SELECT * FROM route_town "+
								"	WHERE id IN (SELECT min(id) FROM route_town "+
								"		GROUP BY route_id)) base ON base.route_id = r.id "+
								"JOIN town t2 ON base.town_id = t2.id "+
								"WHERE 1 = 1 ";
			if (empID != null){statement += "AND e.id = "+empID+" ";}
			if (dTownID != null){statement += "AND t.id = "+dTownID+" ";}
			if (bTownID != null){statement += "AND t2.id = "+bTownID+" ";}
			
			Acc = stmt.executeQuery( statement );	
							
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try {
			while(Acc.next()){
				((DefaultTableModel)t1.getModel()).addRow(new Object[]{Acc.getInt(9), Acc.getString(1)+" "+Acc.getString(2)+" "+Acc.getString(3), Acc.getString(4), Acc.getString(5), Acc.getString(6), Acc.getString(7), Acc.getFloat(8)});
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
