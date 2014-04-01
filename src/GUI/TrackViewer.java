package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import DBMS.ConnEst;
import DBMS.EmplReader;
import DBMS.RouteSaver;
import DBMS.RtEmSaver;
import DBMS.RtTwSaver;
import DBMS.TownReader;
import GPS.Employee;
import GPS.GpxDecoder;
import GPS.Route;
import GPS.Town;

public class TrackViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String filenameGpx;

	ArrayList<SelectEmpl> emps = new ArrayList<SelectEmpl>();

	/**
	 * DEBUG ONLY
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		displayMe(2);
	}

	/**
	 * Zobrazi nove okno s podrobnostami trasy
	 * @param routeID ID trasy v databaze
	 * @return referencia na vytvorene okno 
	 */
	public static TrackViewer displayMe(int routeID){
		final TrackViewer frame = new TrackViewer(routeID);
		EventQueue.invokeLater(new Runnable() {			
			public void run() {
				try {					
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return frame;		
	}
	
	/**
	 * Create the frame.
	 */
	public TrackViewer(final int routeID) {
		setTitle("Route data");
		setBounds(100, 100, 638, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPaneRt = new JScrollPane();
		scrollPaneRt.setBounds(10, 36, 329, 122);
		contentPane.add(scrollPaneRt);
		
		
		final JTextArea rTownTA = new JTextArea();
		rTownTA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		TownReader TR = new TownReader();
		ArrayList<Town> tList = TR.getAllRouteTowns(routeID);
		TR.closeCnn();
		for (Town t: tList){
			rTownTA.append(t.getName()+"\n");
		}
		scrollPaneRt.setViewportView(rTownTA);
		
		JLabel lblTownsOnThe = new JLabel("Towns on the Route:");
		lblTownsOnThe.setBounds(10, 11, 152, 14);
		contentPane.add(lblTownsOnThe);
		
		JButton btnSelectAnotherGpx = new JButton("Select another GPX file");
		btnSelectAnotherGpx.setBounds(10, 169, 329, 23);
		btnSelectAnotherGpx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Town> townsOnTheRt = new ArrayList<Town>();
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "GPX subory", "gpx");
				    fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		          File file = fc.getSelectedFile();
		          filenameGpx = file.getAbsolutePath();
		          GpxDecoder GD = new GpxDecoder(filenameGpx);
		          townsOnTheRt = GD.GetTownList(0.05); /////TODO z konfiguraku
				}
				rTownTA.setText(null);
				for (Town t: townsOnTheRt){
					rTownTA.append(t.getName()+"\n");
				}
			}
		});
		contentPane.add(btnSelectAnotherGpx);
		
		JLabel lblEmployees = new JLabel("Employees:");
		lblEmployees.setBounds(10, 217, 95, 14);
		contentPane.add(lblEmployees);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.setBounds(10, 466, 329, 26);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filenameGpx != null){
					updateRoute(routeID, emps, new GpxDecoder(filenameGpx));
				}
				else {
					updateRoute(routeID, emps, null);
				}
			}
		});
		contentPane.add(btnSave);
		
		JLabel lblSemPrdeMapa = new JLabel("sem pr\u00EDde mapa....");
		lblSemPrdeMapa.setBounds(359, 232, 132, 14);
		contentPane.add(lblSemPrdeMapa);
		
		JScrollPane scrollPaneEmp = new JScrollPane();
		scrollPaneEmp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneEmp.setBounds(10, 244, 329, 211);
		contentPane.add(scrollPaneEmp);
		
		final JPanel selEmpP = new JPanel();
		scrollPaneEmp.setViewportView(selEmpP);
		selEmpP.setLayout(null);
		
		JButton btnAddOther = new JButton("Add other");
		btnAddOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emps.add(new SelectEmpl());
				emps.get(emps.size()-1).setBounds(-10,-50+40*emps.size(),320,39);
				selEmpP.add(emps.get(emps.size()-1));
				selEmpP.repaint();
			}
		});
		btnAddOther.setBounds(93, 213, 89, 23);
		contentPane.add(btnAddOther);
		
		/**
		 * panel so choosermi zamestnancov
		 */
		{
			ArrayList<Employee> assignedEmps = new ArrayList<Employee>();
			EmplReader ER = new EmplReader();
			assignedEmps = ER.getEmpNamesWithinRoute(routeID);
			
			for (Employee e:  assignedEmps){
				emps.add(new SelectEmpl(e.getID()));
				emps.get(emps.size()-1).setBounds(-10,-50+40*emps.size(),320,39);
				selEmpP.add(emps.get(emps.size()-1));				
			}
			
		}
	}
	
	private void updateRoute(int toBeUpdatedID, ArrayList<SelectEmpl> emps, GpxDecoder GD){
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
				RES.save(RES.getAutoIncrPK(), e.posID.get(e.empsCB.getSelectedIndex()), rtToBeSaved.getID());					
			}
		}
		else {
			RES.deleteRow(toBeUpdatedID);
			for (SelectEmpl e: emps){
				RES.save(RES.getAutoIncrPK(), e.posID.get(e.empsCB.getSelectedIndex()), toBeUpdatedID);					
			}
			
		}
		
		dispose();	
		
		CE.commit();
		CE.closeCnn();
		
	}
}
