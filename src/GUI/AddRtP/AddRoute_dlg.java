package GUI.AddRtP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import DBMS.ConnEst;
import DBMS.RouteSaver;
import DBMS.RtEmSaver;
import DBMS.RtTwSaver;
import GPS.GpxDecoder;
import GPS.Route;
import GPS.Town;
import GUI.SelectEmpl;
import GUI.MainWP.MainWCtrl;

public class AddRoute_dlg extends JDialog {

	JButton okButton;
	private static final long serialVersionUID = 1L;
	private JTextField fileCh;
	JPanel routeCh;
	JButton btnAdd;
	JPanel buttonPane;
	ArrayList<SelectEmpl> emps = new ArrayList<SelectEmpl>();
	
	private MainWCtrl parent;
	

	/**
	 * DEBUG ONLY
	 * @param args
	 */
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			AddRoute_dlg dialog = new AddRoute_dlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Zobrazi nove okno a vrati referenciu 
	 * @return
	 */
	public static AddRoute_dlg displayMe(MainWCtrl parent){
		try {
			AddRoute_dlg dialog = new AddRoute_dlg();
			dialog.parent = parent;
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Create the dialog.
	 */
	public AddRoute_dlg() {
		
		setBounds(100, 100, 352, 299);
		getContentPane().setLayout(null);
							
		////////////////// panel Route Chooser
		routeCh = new JPanel();
		routeCh.setBounds(10, 11, 320, 61);
		getContentPane().add(routeCh);
		routeCh.setLayout(null);
		
		fileCh = new JTextField();
		fileCh.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "GPX subory", "gpx");
				    fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		          File file = fc.getSelectedFile();
		          String filename= file.getAbsolutePath();
		          fileCh.setText(filename);
		          okButton.setEnabled(true);
				}
			}			
		});
		
		fileCh.setText("Pick a file...");
		fileCh.setEditable(false);
		fileCh.setColumns(10);
		fileCh.setBounds(10, 36, 185, 20);
		routeCh.add(fileCh);
		
		JLabel label = new JLabel("Route");
		label.setBounds(10, 11, 46, 14);
		routeCh.add(label);
		
		JButton button = new JButton("View");
		button.setBounds(221, 35, 89, 23);
		routeCh.add(button);
				
		
		/////////////////////////// panely so zamestnancami
		JLabel lblEmployees = new JLabel("Employees:");
		lblEmployees.setBounds(20, 83, 81, 14);
		getContentPane().add(lblEmployees);
		
		emps.add(new SelectEmpl());
		emps.get(0).setBounds(10,108,320,39);
		getContentPane().add(emps.get(emps.size()-1));
				
		
		///////////////////////////////panel button Pane
		buttonPane = new JPanel();
		buttonPane.setBounds(10, 161, 320, 88);
		getContentPane().add(buttonPane);
		{
			okButton = new JButton("OK");
			okButton.setEnabled(false);
			okButton.setBounds(188, 54, 47, 23);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SaveRoute(new GpxDecoder(fileCh.getText()));
				}
			});
			buttonPane.setLayout(null);
			
			btnAdd = new JButton("Next employee");
			btnAdd.setBounds(10, 5, 117, 23);
			buttonPane.add(btnAdd);
			btnAdd.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent arg0) {
					setBounds(bounds().x, bounds().y, bounds().width, bounds().height+50);
					buttonPane.setBounds(buttonPane.bounds().x, buttonPane.bounds().y+50, buttonPane.bounds().width, buttonPane.bounds().height);
					emps.add(new SelectEmpl());
					emps.get(emps.size()-1).setBounds(10,58+50*emps.size(),320,39);
					getContentPane().add(emps.get(emps.size()-1));
					
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setBounds(245, 54, 65, 23);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
			
	}
	
	private void SaveRoute(GpxDecoder GD){
		ConnEst CE = new ConnEst();
		CE.beginTrans();
		
		RouteSaver RS = new RouteSaver(CE.getCnn());
	
		Route rtToBeSaved = new Route(RS.getAutoIncrPK() ,GD.getStart(), GD.getEnd(), GD.GetRouteLen()); 
		RS.save(rtToBeSaved);
				
		RtTwSaver RTS = new RtTwSaver(CE.getCnn());
		ArrayList<Town> townsOnTheRt = GD.GetTownList(0.05); /////TODO z konfiguraku
		for (Town t:townsOnTheRt){
			RTS.save(RTS.getAutoIncrPK(), rtToBeSaved.getID(), t.getDbID());
		}
	
		RtEmSaver RES = new RtEmSaver(CE.getCnn());
		for (SelectEmpl e: emps){
			if(e.getPosID().get(e.getEmpsCB().getSelectedIndex()) != -1){
				RES.save(RES.getAutoIncrPK(), e.getPosID().get(e.getEmpsCB().getSelectedIndex()), rtToBeSaved.getID());					
			}
		}
		dispose();	
		
		CE.commit();
		CE.closeCnn();
		
		parent.initialize();
		parent.update();
		
	}
}
