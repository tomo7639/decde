package GUI.ArchViewerP;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import DBMS.ConnEst;
import DBMS.Archive.ArchAllReader;
import DBMS.Archive.ArchEmplReader;
import DBMS.Archive.ArchTownReader;
import DBMS.Archive.ArchiveSaver;
import GPS.DandTime;
import GPS.Employee;
import GPS.Town;
import GUI.MainWP.MainWCtrl;

public class ArchViewerCtrl {

	private ArchViewer view;
	private MainWCtrl parent;
		
	public void displayMe(MainWCtrl parent){
		
		view = new ArchViewer(this);
		this.parent = parent;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
		
		initialize();
		update();
	}
	
	protected void update(){
		DefaultTableModel dm = (DefaultTableModel) view.tableRts.getModel();
		while(dm.getRowCount()>0){
			dm.removeRow(0);
		}
		ArchAllReader AR = new ArchAllReader();
		AR.read(view.tableRts, view.posIDemp.get(view.empCB.getSelectedIndex()), view.posIDdest.get(view.destCB.getSelectedIndex()), view.posIDbase.get(view.baseCB.getSelectedIndex()),
				new DandTime(view.stFromDC.getDate()), new DandTime(view.stToDC.getDate()), new DandTime(view.endFromDC.getDate()), new DandTime(view.endToDC.getDate()));
		AR.closeCnn();
	}
	
	protected void initialize(){
		ArchEmplReader ER = new ArchEmplReader();
		ArrayList<Employee> empls = ER.getAllEmpNames();
		ER.closeCnn();
		for (int i=0;i<empls.size();i++){
			Employee s = empls.get(i);
			view.empCB.addItem(s.getFName()+" "+s.getSName()+" "+s.getDName());
			view.posIDemp.put(i+1, s.getID());
		}
		
		ArchTownReader TR = new ArchTownReader();
		ArrayList<Town> towns = TR.getAllDestTowns();
		for (int i=0;i<towns.size();i++){
			Town t = towns.get(i);
			view.destCB.addItem(t.getName());
			view.posIDdest.put(i+1, t.getDbID());
		}
		
		towns = TR.getAllBaseTowns();
		for (int i=0;i<towns.size();i++){
			Town t = towns.get(i);
			view.baseCB.addItem(t.getName());
			view.posIDbase.put(i+1, t.getDbID());
		}				
	}
	
	protected void bringFromArchive(){
		DefaultTableModel dtm = (DefaultTableModel)view.tableRts.getModel();
		int nRow = dtm.getRowCount();
	    ArrayList<Integer> IDs = new ArrayList<Integer>(); 
	    for (int i = 0; i < nRow; i++) {
			IDs.add((Integer) dtm.getValueAt(i,1));
		}
		
		ConnEst CE = new ConnEst();
		ArchiveSaver AS = new ArchiveSaver(CE.getCnn());
		
		CE.beginTrans();
		AS.bringFromArchive(IDs);	
		
		CE.commit();
		CE.closeCnn();
		
		update();
		parent.initialize();
		parent.update();
		
	}
	
}
