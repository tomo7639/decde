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
import DBMS.TownReader;
import GPS.Employee;
import GPS.Town;

import com.toedter.calendar.JDateChooser;

public class MainW extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JComboBox<String> empCB;
	private HashMap<Integer, Integer> posIDemp = new HashMap<Integer, Integer>();
	private JComboBox<String> destCB;
	private HashMap<Integer, Integer> posIDdest = new HashMap<Integer, Integer>();
	private JComboBox<String> baseCB;
	private HashMap<Integer, Integer> posIDbase = new HashMap<Integer, Integer>();	

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
		setBounds(100, 100, 878, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 842, 191);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					TrackViewer.displayMe((int)table.getValueAt(table.rowAtPoint(e.getPoint()) ,0));
				}
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] {"ID", "Employee", "Start Time", "End Time", "Destination", "Base", "Kilometres"}) {
		    		private static final long serialVersionUID = 1L;
		    		@Override
		    		public boolean isCellEditable(int row, int column) {
		    			return false;
		    		}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(19);
		table.getColumnModel().getColumn(1).setPreferredWidth(174);
		table.getColumnModel().getColumn(2).setPreferredWidth(139);
		table.getColumnModel().getColumn(3).setPreferredWidth(139);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(45);
		scrollPane.setViewportView(table);
		
		empCB = new JComboBox<String>();
		empCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		empCB.setBounds(56, 48, 199, 20);
		contentPane.add(empCB);
		ArrayList<Employee> empls = EmplReader.getAllEmpNames();
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
		
		final JDateChooser dC1 = new JDateChooser();
		dC1.setBounds(335, 27, 73, 20);
		dC1.addPropertyChangeListener(new PropertyChangeListener() {
	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	            //if(dC1.getDate()!=null) System.out.println(dC1.getDate().toString());
	        }
	    }); 
		contentPane.add(dC1);	
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(335, 48, 73, 20);
		contentPane.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(499, 27, 73, 20);
		contentPane.add(dateChooser_1);
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(499, 48, 73, 20);
		contentPane.add(dateChooser_2);
				
		update();
	}
	
	private void update(){
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		while(dm.getRowCount()>0){
			dm.removeRow(0);
		}
		AllReader AR = new AllReader();
		AR.read(table, posIDemp.get(empCB.getSelectedIndex()), posIDdest.get(destCB.getSelectedIndex()), posIDbase.get(baseCB.getSelectedIndex()));
		AR.closeCnn();
	}
}
