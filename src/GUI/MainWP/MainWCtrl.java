package GUI.MainWP;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import DBMS.AllReader;
import DBMS.ConnEst;
import DBMS.EmplReader;
import DBMS.StatReader;
import DBMS.TownReader;
import DBMS.Archive.ArchiveSaver;
import GPS.DandTime;
import GPS.Employee;
import GPS.Town;
import GUI.ArchViewerP.ArchViewerCtrl;

public class MainWCtrl {

	private MainW view;
		
	public void displayMe(){
		
		view = new MainW(this);
	
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
		
	public void initialize(){
		
		int itemCount = view.empCB.getItemCount();
	    for(int i=0;i<itemCount-1;i++){
	        view.empCB.removeItemAt(1);
	     }
	    itemCount = view.destCB.getItemCount();
	    for(int i=0;i<itemCount-1;i++){
	        view.destCB.removeItemAt(1);
	     }
	    itemCount = view.baseCB.getItemCount();
	    for(int i=0;i<itemCount-1;i++){
	        view.baseCB.removeItemAt(1);
	     }
	    view.posIDbase = new HashMap<Integer, Integer>();
	    view.posIDdest = new HashMap<Integer, Integer>();
	    view.posIDemp = new HashMap<Integer, Integer>();
	    
		EmplReader ER = new EmplReader();
		ArrayList<Employee> empls = ER.getAllEmpNames();
		ER.closeCnn();
		for (int i=0;i<empls.size();i++){
			Employee s = empls.get(i);
			view.empCB.addItem(s.getFName()+" "+s.getSName()+" "+s.getDName());
			view.posIDemp.put(i+1, s.getID());
		}
		
		TownReader TR = new TownReader();
		
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
		TR.closeCnn();
	}
	
	protected void getStats(){
		DefaultTableModel dm = (DefaultTableModel) view.tableStats.getModel();
		while(dm.getRowCount()>0){
			dm.removeRow(0);
		}
		StatReader SR = new StatReader();
		SR.read(view.tableStats, new DandTime(view.kmsFromDC.getDate()), new DandTime(view.kmsToDC.getDate()));
		SR.closeCnn();
	}
	
	protected void sendToArch(){
		DefaultTableModel dtm = (DefaultTableModel) view.tableRts.getModel();
	    int nRow = dtm.getRowCount();
	    ArrayList<Integer> IDs = new ArrayList<Integer>(); 
	    for (int i = 0; i < nRow; i++) {
			IDs.add((Integer) dtm.getValueAt(i,1));
		}
		
		ConnEst CE = new ConnEst();
		ArchiveSaver AS = new ArchiveSaver(CE.getCnn());
		
		CE.beginTrans();
		AS.sendToArchive(IDs);	
		
		CE.commit();
		CE.closeCnn();
		
		initialize();
		update();
	}
	
	public void update(){
		DefaultTableModel dm = (DefaultTableModel) view.tableRts.getModel();
		while(dm.getRowCount()>0){
			dm.removeRow(0);
		}
		AllReader AR = new AllReader();
		AR.read(view.tableRts, view.posIDemp.get(view.empCB.getSelectedIndex()), view.posIDdest.get(view.destCB.getSelectedIndex()), view.posIDbase.get(view.baseCB.getSelectedIndex()),
				new DandTime(view.stFromDC.getDate()), new DandTime(view.stToDC.getDate()), new DandTime(view.endFromDC.getDate()), new DandTime(view.endToDC.getDate()));
		AR.closeCnn();		
	}
	
	protected void showArchive(){
		ArchViewerCtrl awc = new ArchViewerCtrl();
		awc.displayMe(this);
	}
}
