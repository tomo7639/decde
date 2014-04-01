package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBMS.AllReader;
import DBMS.EmplReader;
import DBMS.StatReader;
import DBMS.TownReader;
import GPS.DandTime;
import GPS.Employee;
import GPS.Town;

import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;

public class MainW extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRts;
	private JComboBox<String> empCB;
	private HashMap<Integer, Integer> posIDemp = new HashMap<Integer, Integer>();
	private JComboBox<String> destCB;
	private HashMap<Integer, Integer> posIDdest = new HashMap<Integer, Integer>();
	private JComboBox<String> baseCB;
	private HashMap<Integer, Integer> posIDbase = new HashMap<Integer, Integer>();	
	private JTable tableStats;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainW frame = new MainW();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainW() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 878, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 842, 191);
		contentPane.add(scrollPane);
		
		tableRts = new JTable();
		tableRts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					TrackViewer.displayMe((int)tableRts.getValueAt(tableRts.rowAtPoint(e.getPoint()) ,0));
				}
			}
		});
		tableRts.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] {"ID", "Employee", "Start Time", "End Time", "Destination", "Base", "Kilometres"}) {
		    		private static final long serialVersionUID = 1L;
		    		@Override
		    		public boolean isCellEditable(int row, int column) {
		    			return false;
		    		}
		});
		
		tableRts.getColumnModel().getColumn(0).setPreferredWidth(19);
		tableRts.getColumnModel().getColumn(1).setPreferredWidth(174);
		tableRts.getColumnModel().getColumn(2).setPreferredWidth(139);
		tableRts.getColumnModel().getColumn(3).setPreferredWidth(139);
		tableRts.getColumnModel().getColumn(4).setPreferredWidth(70);
		tableRts.getColumnModel().getColumn(5).setPreferredWidth(70);
		tableRts.getColumnModel().getColumn(6).setPreferredWidth(45);
		scrollPane.setViewportView(tableRts);
		
		empCB = new JComboBox<String>();
		empCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		empCB.setBounds(56, 48, 199, 20);
		contentPane.add(empCB);
		EmplReader ER = new EmplReader();
		ArrayList<Employee> empls = ER.getAllEmpNames();
		ER.closeCnn();
		for (int i=0;i<empls.size();i++){
			Employee s = empls.get(i);
			empCB.addItem(s.getFName()+" "+s.getSName()+" "+s.getDName());
			posIDemp.put(i+1, s.getID());
		}

		TownReader TR = new TownReader();
		
		destCB = new JComboBox<String>();
		destCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		destCB.setBounds(586, 48, 96, 20);
		contentPane.add(destCB);
		ArrayList<Town> towns = TR.getAllDestTowns();
		for (int i=0;i<towns.size();i++){
			Town t = towns.get(i);
			destCB.addItem(t.getName());
			posIDdest.put(i+1, t.getDbID());
		}
		
		baseCB = new JComboBox<String>();
		baseCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		baseCB.setBounds(683, 48, 96, 20);
		contentPane.add(baseCB);		
		towns = TR.getAllBaseTowns();
		for (int i=0;i<towns.size();i++){
			Town t = towns.get(i);
			baseCB.addItem(t.getName());
			posIDbase.put(i+1, t.getDbID());
		}
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		btnFilter.setBounds(10, 14, 89, 23);
		contentPane.add(btnFilter);
		
		final JDateChooser stFromDC = new JDateChooser();
		stFromDC.setBounds(335, 27, 73, 20);
		stFromDC.addPropertyChangeListener(new PropertyChangeListener() {
	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	        	
	        }
	    }); 
		contentPane.add(stFromDC);	
		
		JDateChooser stToDC = new JDateChooser();
		stToDC.setBounds(335, 48, 73, 20);
		contentPane.add(stToDC);
		
		JDateChooser endFromDC = new JDateChooser();
		endFromDC.setBounds(499, 27, 73, 20);
		contentPane.add(endFromDC);
		
		JDateChooser endToDC = new JDateChooser();
		endToDC.setBounds(499, 48, 73, 20);
		contentPane.add(endToDC);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 314, 418, 204);
		contentPane.add(scrollPane_1);
		
		tableStats = new JTable();
		tableStats.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Employee", "Driven kms"
			}
		));
		tableStats.getColumnModel().getColumn(0).setPreferredWidth(31);
		tableStats.getColumnModel().getColumn(1).setPreferredWidth(190);
		scrollPane_1.setViewportView(tableStats);
		
		final JDateChooser kmsFromDC = new JDateChooser();
		kmsFromDC.setBounds(69, 283, 96, 20);
		contentPane.add(kmsFromDC);
		
		final JDateChooser kmsToDC = new JDateChooser();
		kmsToDC.setBounds(233, 283, 96, 20);
		contentPane.add(kmsToDC);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel dm = (DefaultTableModel) tableStats.getModel();
				while(dm.getRowCount()>0){
					dm.removeRow(0);
				}
				StatReader SR = new StatReader();
				SR.read(tableStats, new DandTime(kmsFromDC.getDate()), new DandTime(kmsToDC.getDate()));
				//SR.closeCnn();
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
				
		update();
	}
	
	private void update(){
		DefaultTableModel dm = (DefaultTableModel) tableRts.getModel();
		while(dm.getRowCount()>0){
			dm.removeRow(0);
		}
		AllReader AR = new AllReader();
		AR.read(tableRts, posIDemp.get(empCB.getSelectedIndex()), posIDdest.get(destCB.getSelectedIndex()), posIDbase.get(baseCB.getSelectedIndex()));
		AR.closeCnn();
	}
	
	
}
