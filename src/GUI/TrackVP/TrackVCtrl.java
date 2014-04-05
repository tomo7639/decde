package GUI.TrackVP;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import DBMS.ConnEst;
import DBMS.EmplReader;
import DBMS.RouteSaver;
import DBMS.RtEmSaver;
import DBMS.RtTwSaver;
import DBMS.TownReader;
import DBMS.Archive.ArchiveSaver;
import GPS.Employee;
import GPS.GpxDecoder;
import GPS.Route;
import GPS.Town;
import GUI.SelectEmpl;
import GUI.MainWP.MainWCtrl;

public class TrackVCtrl {
	
	private TrackViewer view;
	private int routeID;
	private MainWCtrl parent;
	
	/**
	 * Zobrazi nove okno s podrobnostami trasy
	 * @param routeID ID trasy v databaze
	 **/
	public void displayMe(int routeID, MainWCtrl parent){
				
		this.routeID = routeID;
		this.parent = parent;
			
		view = new TrackViewer(routeID, this);
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
	}
	
	protected void initialize(){
		TownReader TR = new TownReader();
		ArrayList<Town> tList = TR.getAllRouteTowns(routeID);
		TR.closeCnn();
		for (Town t: tList){
			view.rTownTA.append(t.getName()+"\n");
		}
		
		ArrayList<Employee> assignedEmps = new ArrayList<Employee>();
		EmplReader ER = new EmplReader();
		assignedEmps = ER.getEmpNamesWithinRoute(routeID);
		
		for (Employee e:  assignedEmps){
			view.emps.add(new SelectEmpl(e.getID()));
			view.emps.get(view.emps.size()-1).setBounds(-10,-50+40*view.emps.size(),320,39);
			view.selEmpP.add(view.emps.get(view.emps.size()-1));				
		}
	}
	
	protected void changeFile(){
		ArrayList<Town> townsOnTheRt = new ArrayList<Town>();
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "GPX subory", "gpx");
		    fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          view.filenameGpx = file.getAbsolutePath();
          GpxDecoder GD = new GpxDecoder(view.filenameGpx);
          townsOnTheRt = GD.GetTownList(0.05); /////TODO z konfiguraku
		}
		view.rTownTA.setText(null);
		for (Town t: townsOnTheRt){
			view.rTownTA.append(t.getName()+"\n");
		}
	}
	
	protected void addEPanel(){
		view.emps.add(new SelectEmpl());
		view.emps.get(view.emps.size()-1).setBounds(-10,-50+40*view.emps.size(),320,39);
		view.selEmpP.add(view.emps.get(view.emps.size()-1));
		view.selEmpP.setPreferredSize(new Dimension(300, 1000));
		view.selEmpP.repaint();
		
	}
	
	protected void updateRoute(int toBeUpdatedID, ArrayList<SelectEmpl> emps, GpxDecoder GD){
		ConnEst CE = new ConnEst();
		CE.beginTrans();
		
		RouteSaver RS = new RouteSaver(CE.getCnn());
		RtTwSaver RTS = new RtTwSaver(CE.getCnn());
		RtEmSaver RES = new RtEmSaver(CE.getCnn());

		if (GD != null){
			RES.deleteRow(toBeUpdatedID);
			RTS.deleteRow(toBeUpdatedID);
			RS.deleteRow(toBeUpdatedID);
			Route rtToBeSaved = new Route(RS.getAutoIncrPK() ,GD.getStart(), GD.getEnd(), GD.GetRouteLen()); 
			RS.save(rtToBeSaved);
					
			ArrayList<Town> townsOnTheRt = GD.GetTownList(0.05); /////TODO z konfiguraku
			for (Town t:townsOnTheRt){
				RTS.save(RTS.getAutoIncrPK(), rtToBeSaved.getID(), t.getDbID());
			}		
			for (SelectEmpl e: emps){
				RES.save(RES.getAutoIncrPK(), e.getPosID().get(e.getEmpsCB().getSelectedIndex()), rtToBeSaved.getID());					
			}
		}
		else {
			RES.deleteRow(toBeUpdatedID);
			for (SelectEmpl e: emps){
				if(e.getPosID().get(e.getEmpsCB().getSelectedIndex()) != -1){
					RES.save(RES.getAutoIncrPK(), e.getPosID().get(e.getEmpsCB().getSelectedIndex()), toBeUpdatedID);
				}
			}			
		}
		ArchiveSaver AS = new ArchiveSaver(CE.getCnn());
		AS.cleanUp();		
		
		CE.commit();
		CE.closeCnn();
		
		view.dispose();	
		parent.initialize();
		parent.update();
		
	}
}
