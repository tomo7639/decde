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
	
	public static void main(String[] args){
		
	}
	
	
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
			String statement = 	"SELECT emp_fname, emp_sname, emp_dname, rstart, rend, c.town, c2.town, kms, r.rt_id FROM route r "+
								"JOIN route_employee re ON r.rt_id = re.rt_id "+
								"JOIN employees e ON e.emp_id = re.emp_id "+
								"JOIN(SELECT * FROM route_town "+
								"	WHERE id IN (SELECT max(id) FROM route_town "+
								"		GROUP BY route_id)) dest ON dest.route_id = r.rt_id "+
								"JOIN cities c ON dest.town_id = c.id "+
								"JOIN(SELECT * FROM route_town "+
								"	WHERE id IN (SELECT min(id) FROM route_town "+
								"		GROUP BY route_id)) base ON base.route_id = r.rt_id "+
								"JOIN cities c2 ON base.town_id = c2.id ";
			if (empID != null){
				statement += "WHERE e.emp_id = "+empID+" ";
			}
			if (dTownID != null){
				if (empID == null){
					statement += "WHERE c.id = "+dTownID+" ";
				}
				else{
					statement += "AND c.id = "+dTownID+" ";
				}
			}
			if (bTownID != null){
				if (empID == null && dTownID == null){
					statement += "WHERE c2.id = "+bTownID+" ";
				}
				else{
					statement += "AND c2.id = "+bTownID+" ";
				}
			}
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
