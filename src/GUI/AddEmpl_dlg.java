package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import DBMS.ConnEst;
import DBMS.EmplSaver;
import GPS.Employee;

public class AddEmpl_dlg extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtFName;
	private JTextField txtSName;
	private JTextField txtDName;
	private JButton okButton;
	private JButton cancelButton;

		
	/**
	 * zobrazi nove okno a vrati referenciu nan
	 **/
	public static void displayMe(SelectEmpl parent) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			AddEmpl_dlg dialog = new AddEmpl_dlg(parent);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
			
	/**
	 * Create the dialog.
	 */
	public AddEmpl_dlg(final SelectEmpl parent) {
		setTitle("Assign new Route to new Employee");
		setBounds(100, 100, 450, 176);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setBounds(35, 11, 91, 14);
		contentPanel.add(lblEmployee);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(35, 30, 91, 14);
		contentPanel.add(lblFirstName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(163, 30, 59, 14);
		contentPanel.add(lblSurname);
		
		txtFName = new JTextField();
		txtFName.setBounds(35, 49, 106, 20);
		contentPanel.add(txtFName);
		txtFName.setColumns(10);
		
		txtSName = new JTextField();
		txtSName.setBounds(163, 49, 126, 20);
		contentPanel.add(txtSName);
		txtSName.setColumns(10);
		
		JLabel lblTit = new JLabel("Degree");
		lblTit.setBounds(310, 30, 46, 14);
		contentPanel.add(lblTit);
		
		txtDName = new JTextField();
		txtDName.setBounds(310, 49, 86, 20);
		contentPanel.add(txtDName);
		txtDName.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 96, 434, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ConnEst CE = new ConnEst();
						EmplSaver ES = new EmplSaver(CE.getCnn());
						Employee toBeSaved = new Employee(ES.getAutoIncrPK(), txtFName.getText(), txtSName.getText(), txtDName.getText());
						ES.save(toBeSaved);
						CE.closeCnn();
						
						parent.empsCB.addItem(txtFName.getText()+" "+txtSName.getText()+" "+txtDName.getText());
						parent.empsCB.setSelectedIndex(parent.empsCB.getItemCount()-1);
						parent.posID.put(parent.empsCB.getItemCount()-1, toBeSaved.getID());
						parent.empsCB.setEnabled(false);    //toto sa stura, kam by sa nemalo,,, ako na to?
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				okButton.setEnabled(true);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String getFName(){
		return txtFName.getText();
	}
	
	public String getSName(){
		return txtSName.getText();
	}
	
	public String getDName(){
		return txtDName.getText();
	}
	
}
