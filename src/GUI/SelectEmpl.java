package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import DBMS.EmplReader;
import GPS.Employee;

public class SelectEmpl extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> empsCB;
	private Map<Integer, Integer> posID;
	Map<Integer, Integer> revPosID;
	/**
	 * Create the panel.
	 */
	public SelectEmpl() {
		initialize();
	}
	
	public SelectEmpl(int empID) {
		initialize();
		getEmpsCB().setSelectedIndex(revPosID.get(empID));
	}
	
	public void addNew(String name, int dbID){
		getEmpsCB().addItem(name);
		getEmpsCB().setSelectedIndex(getEmpsCB().getItemCount()-1);
		getPosID().put(getEmpsCB().getItemCount()-1, dbID);
		getEmpsCB().setEnabled(false); 
	}
	
	private void initialize(){
		setLayout(null);		
		setEmpsCB(new JComboBox<String>());
		getEmpsCB().setBounds(10, 11, 204, 20);
		setPosID(new HashMap<Integer, Integer>());
		revPosID = new HashMap<Integer, Integer>();
		EmplReader ER = new EmplReader();
		ArrayList<Employee> empls = ER.getAllEmpNames();
		ER.closeCnn();
		for (int i=0;i<empls.size();i++){
			Employee s = empls.get(i);
			getEmpsCB().addItem(s.getFName()+" "+s.getSName()+" "+s.getDName());
			getPosID().put(i, s.getID());
			revPosID.put(s.getID(), i);
		}
		add(getEmpsCB());
		
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

	public Map<Integer, Integer> getPosID() {
		return posID;
	}

	public void setPosID(Map<Integer, Integer> posID) {
		this.posID = posID;
	}

	public JComboBox<String> getEmpsCB() {
		return empsCB;
	}

	public void setEmpsCB(JComboBox<String> empsCB) {
		this.empsCB = empsCB;
	}
	
	
}
