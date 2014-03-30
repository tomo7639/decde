package GUI;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import decde.First;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JPasswordField;

public class Logon_dlg extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTextField tf_uid;
	private JPasswordField tf_pwd;
	
	public static Logon_dlg displayMe(){
		try {
			Logon_dlg dialog = new Logon_dlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}	
	
	public Logon_dlg() {
		setBounds(100, 100, 339, 307);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 228, 317, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent arg0) {
						try {
							Class.forName("org.postgresql.Driver");
							Connection lgFlow = DriverManager.getConnection(
									"jdbc:postgresql://localhost:5432/USER", "postgres","VAVADBS");
							Statement stmt = lgFlow.createStatement();
							ResultSet userAcc = stmt.executeQuery( "SELECT * FROM user_db WHERE u_id = '"+tf_uid.getText()+"'");
							if ( userAcc.next() ) {
								if (userAcc.getInt("u_pwd") != hash(tf_pwd.getText()) ){
									 JOptionPane.showMessageDialog(null,"Wrong user name or passwd"+hash(tf_pwd.getText()));
					          	     }
								else{
									 dispose();
									 First.go();
					            }  							
							}
							else{
								JOptionPane.showMessageDialog(null,"Wrong user name or passwd"+hash(tf_pwd.getText()));
							}
							
						} catch (Exception e) {
							System.err.println("blablabla");
						}					
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			
			
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}						
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setBounds(44, 73, 82, 14);
		getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(44, 142, 46, 14);
		getContentPane().add(lblPassword);
		
		JLabel lblPleaseLogIn = new JLabel("Please log in with your user name and password");
		lblPleaseLogIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPleaseLogIn.setBounds(10, 22, 297, 14);
		getContentPane().add(lblPleaseLogIn);
		
		tf_uid = new JTextField();
		tf_uid.setBounds(69, 98, 166, 20);
		getContentPane().add(tf_uid);
		tf_uid.setColumns(10);
		
		tf_pwd = new JPasswordField();
		tf_pwd.setBounds(69, 170, 166, 20);
		getContentPane().add(tf_pwd);
	}
	
	private int hash(String upwd){
		Integer res=0;
		for (int i=0;i<upwd.length();i++){
			res = res  * 151 + upwd.charAt(i);
		}
		return res;
	}
	
}
