package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import DBMS.EmplReader;
import GPS.Employee;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectEmpl extends JPanel {

	private static final long serialVersionUID = 1L;
	JComboBox<String> empsCB;
	Map<Integer, Integer> posID;
	/**
	 * Create the panel.
	 */
	public SelectEmpl() {
		setLayout(null);		
		empsCB = new JComboBox<String>();
		empsCB.setBounds(10, 11, 204, 20);
		posID = new HashMap<Integer, Integer>();
		ArrayList<Employee> empls = EmplReader.getAllEmpNames();
		for (int i=0;i<empls.size();i++){
			Employee s = empls.get(i);
			empsCB.addItem(s.getFName()+" "+s.getSName()+" "+s.getDName());
			posID.put(i, s.getID());
		}
		add(empsCB);
		
		JButton btnOther = new JButton("Other...");
		final SelectEmpl xyz = this;
		btnOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEmpl_dlg.displayMe(xyz);
			}
		});
		btnOther.setBounds(224, 10, 83, 23);
		add(btnOther);

	}
}
