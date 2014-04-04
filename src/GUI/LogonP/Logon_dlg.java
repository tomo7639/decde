package GUI.LogonP;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Logon_dlg extends JDialog {
	
	private static final long serialVersionUID = 1L;
	protected JTextField tf_uid;
	protected JPasswordField tf_pwd;
	
		
	public Logon_dlg(final LogonDCtrl ctrl) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
					public void actionPerformed(ActionEvent arg0) {
						ctrl.logOn();			
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			
			
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(1);
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
		
}
