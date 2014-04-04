package GUI.LogonP;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import GUI.MainWP.MainWCtrl;

public class LogonDCtrl {

	private Logon_dlg view;
	
	public static void main(String []args){
		LogonDCtrl ldc = new LogonDCtrl();
		ldc.displayMe();
	}
	
	public void displayMe(){		
		view = new Logon_dlg(this);		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});				
	}
	
	@SuppressWarnings("deprecation")
	protected void logOn(){
		try {
			Class.forName("org.postgresql.Driver");
			Connection lgFlow = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/USER", "postgres","VAVADBS");
			Statement stmt = lgFlow.createStatement();
			ResultSet userAcc = stmt.executeQuery( "SELECT * FROM user_db WHERE u_id = '"+view.tf_uid.getText()+"'");
			if ( userAcc.next() ) {
				if (userAcc.getInt("u_pwd") != hash(view.tf_pwd.getText()) ){
					 JOptionPane.showMessageDialog(null,"Wrong user name or passwd"+hash(view.tf_pwd.getText()));
	          	     }	
				else{
					view.dispose();
					MainWCtrl mwc = new MainWCtrl();
					mwc.displayMe();
				}
			}
			else{
				JOptionPane.showMessageDialog(null,"Wrong user name or passwd"+hash(view.tf_pwd.getText()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private int hash(String upwd){
		Integer res=0;
		for (int i=0;i<upwd.length();i++){
			res = res  * 151 + upwd.charAt(i);
		}
		return res;
	}

}
