package GUI.MainWP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import GUI.TrackVP.TrackVCtrl;

import com.toedter.calendar.JDateChooser;

public class MainW extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JTable tableRts;
	protected JComboBox<String> empCB;
	protected HashMap<Integer, Integer> posIDemp = new HashMap<Integer, Integer>();
	protected JComboBox<String> destCB;
	protected HashMap<Integer, Integer> posIDdest = new HashMap<Integer, Integer>();
	protected JComboBox<String> baseCB;
	protected HashMap<Integer, Integer> posIDbase = new HashMap<Integer, Integer>();	
	protected JTable tableStats;
	protected final JDateChooser stFromDC;
	protected final JDateChooser stToDC;
	protected final JDateChooser endFromDC;
	protected final JDateChooser endToDC;
	protected final JDateChooser kmsToDC;
	protected final JDateChooser kmsFromDC;

	
	private MainWCtrl ctrl;
	
	/**
	 * Create the frame.
	 */
	protected MainW(MainWCtrl controller) {
		
		this.ctrl = controller;
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 878, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPaneRts = new JScrollPane();
		scrollPaneRts.setBounds(10, 69, 842, 191);
		contentPane.add(scrollPaneRts);
		
		tableRts = new JTable();
		tableRts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					TrackVCtrl tvs = new TrackVCtrl();
					tvs.displayMe((int)tableRts.getValueAt(tableRts.rowAtPoint(e.getPoint()) ,0));
				}
			}
		});
		tableRts.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] {"IDr", "IDre", "Employee", "Start Time", "End Time", "Destination", "Base", "Kilometres"}) {
    		private static final long serialVersionUID = 1L;
    		@Override
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
			});
		
		tableRts.getColumnModel().getColumn(0).setPreferredWidth(0);
		tableRts.getColumnModel().getColumn(1).setPreferredWidth(19);
		tableRts.getColumnModel().getColumn(2).setPreferredWidth(174);
		tableRts.getColumnModel().getColumn(3).setPreferredWidth(139);
		tableRts.getColumnModel().getColumn(4).setPreferredWidth(139);
		tableRts.getColumnModel().getColumn(5).setPreferredWidth(70);
		tableRts.getColumnModel().getColumn(6).setPreferredWidth(70);
		tableRts.getColumnModel().getColumn(7).setPreferredWidth(45);
		scrollPaneRts.setViewportView(tableRts);
		
		empCB = new JComboBox<String>();
		empCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		empCB.setBounds(56, 48, 199, 20);
		contentPane.add(empCB);
	
		destCB = new JComboBox<String>();
		destCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		destCB.setBounds(586, 48, 96, 20);
		contentPane.add(destCB);
				
		baseCB = new JComboBox<String>();
		baseCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		baseCB.setBounds(683, 48, 96, 20);
		contentPane.add(baseCB);		
				
		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.update();
			}
		});
		btnFilter.setBounds(10, 14, 89, 23);
		contentPane.add(btnFilter);
		
		stFromDC = new JDateChooser();
		stFromDC.setBounds(335, 27, 73, 20);
		contentPane.add(stFromDC);	
		
		stToDC = new JDateChooser();
		stToDC.setBounds(335, 48, 73, 20);
		contentPane.add(stToDC);
		
		endFromDC = new JDateChooser();
		endFromDC.setBounds(499, 27, 73, 20);
		contentPane.add(endFromDC);
		
		endToDC = new JDateChooser();
		endToDC.setBounds(499, 48, 73, 20);
		contentPane.add(endToDC);
		
		JScrollPane scrollPaneStats = new JScrollPane();
		scrollPaneStats.setBounds(10, 314, 418, 204);
		contentPane.add(scrollPaneStats);
		
		tableStats = new JTable();
		tableStats.setModel(new DefaultTableModel(
			new Object[][] {}, new String[] {"ID", "Employee", "Driven kms"}) {
			private static final long serialVersionUID = 1L;
    		@Override
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
		});
		
		tableStats.getColumnModel().getColumn(0).setPreferredWidth(31);
		tableStats.getColumnModel().getColumn(1).setPreferredWidth(190);
		scrollPaneStats.setViewportView(tableStats);
		
		kmsFromDC = new JDateChooser();
		kmsFromDC.setBounds(69, 283, 96, 20);
		contentPane.add(kmsFromDC);
		
		kmsToDC = new JDateChooser();
		kmsToDC.setBounds(233, 283, 96, 20);
		contentPane.add(kmsToDC);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.getStats();
			}
		});
		btnShow.setBounds(339, 280, 89, 23);
		contentPane.add(btnShow);
		
		JLabel lblBegin = new JLabel("Begin");
		lblBegin.setBounds(27, 289, 46, 14);
		contentPane.add(lblBegin);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setBounds(192, 289, 46, 14);
		contentPane.add(lblEnd);
		
		JButton btnSendSelectedTo = new JButton("Send selected to archive");
		btnSendSelectedTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    ctrl.sendToArch();
			}
		});
		btnSendSelectedTo.setBounds(503, 271, 193, 23);
		contentPane.add(btnSendSelectedTo);
		
		JButton btnViewArchive = new JButton("View archive");
		btnViewArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.showArchive();
			}
		});
		btnViewArchive.setBounds(502, 305, 194, 23);
		contentPane.add(btnViewArchive);
				
	}	
}
