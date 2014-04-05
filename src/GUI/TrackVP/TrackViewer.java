package GUI.TrackVP;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import GPS.GpxDecoder;
import GUI.SelectEmpl;

public class TrackViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final protected JTextArea rTownTA;
	protected String filenameGpx;
	protected final JPanel selEmpP;

	ArrayList<SelectEmpl> emps = new ArrayList<SelectEmpl>();

	/**
	 * Create the frame.
	 */
	public TrackViewer(final int routeID, final TrackVCtrl ctrl) {
		setTitle("Route data");
		setBounds(100, 100, 1039, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPaneRt = new JScrollPane();
		scrollPaneRt.setBounds(10, 36, 329, 122);
		contentPane.add(scrollPaneRt);
				
		rTownTA = new JTextArea();
		rTownTA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPaneRt.setViewportView(rTownTA);
		
		JLabel lblTownsOnThe = new JLabel("Towns on the Route:");
		lblTownsOnThe.setBounds(10, 11, 152, 14);
		contentPane.add(lblTownsOnThe);
		
		JButton btnSelectAnotherGpx = new JButton("Select another GPX file");
		btnSelectAnotherGpx.setBounds(10, 169, 329, 23);
		btnSelectAnotherGpx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.changeFile();
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
					ctrl.updateRoute(routeID, emps, new GpxDecoder(filenameGpx));
				}
				else {
					ctrl.updateRoute(routeID, emps, null);
				}
			}
		});
		contentPane.add(btnSave);
		
		JLabel lblSemPrdeMapa = new JLabel("sem pr\u00EDde mapa....");
		lblSemPrdeMapa.setBounds(359, 232, 132, 14);
		contentPane.add(lblSemPrdeMapa);
		
		final JScrollPane scrollPaneEmp = new JScrollPane();
		scrollPaneEmp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneEmp.setBounds(10, 244, 329, 211);
		contentPane.add(scrollPaneEmp);
		
		selEmpP = new JPanel();
		scrollPaneEmp.setViewportView(selEmpP);
		selEmpP.setLayout(null);
		
		JButton btnAddOther = new JButton("Add other");
		btnAddOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.addEPanel();
			}
		});
		btnAddOther.setBounds(93, 213, 89, 23);
		contentPane.add(btnAddOther);
		
		JButton btnPrint = new JButton("PRINT");
		btnPrint.setBounds(10, 517, 329, 23);
		contentPane.add(btnPrint);
		
		JLabel lblZTohtoBude = new JLabel("z tohto bude tabulka mesto - cas");
		lblZTohtoBude.setBounds(359, 109, 275, 14);
		contentPane.add(lblZTohtoBude);
				
	}
		
}
