package GUI.ArchViewerP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;

public class ArchViewer extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JTable tableRts;
	protected JComboBox<String> empCB;
	protected HashMap<Integer, Integer> posIDemp = new HashMap<Integer, Integer>();
	protected JComboBox<String> destCB;
	protected HashMap<Integer, Integer> posIDdest = new HashMap<Integer, Integer>();
	protected JComboBox<String> baseCB;
	protected HashMap<Integer, Integer> posIDbase = new HashMap<Integer, Integer>();	
	protected final JDateChooser stFromDC;
	protected final JDateChooser stToDC;
	protected final JDateChooser endFromDC;
	protected final JDateChooser endToDC;
	protected JButton btnBringSelectedBack;
	
	protected ArchViewerCtrl ctrl;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;

	
	/**
	 * Create the frame.
	 */
	public ArchViewer(final ArchViewerCtrl ctrl) {
		setTitle("Archive");
		
		this.ctrl=ctrl;
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		setBounds(100, 100, 877, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPaneRts = new JScrollPane();
		scrollPaneRts.setBounds(10, 69, 842, 191);
		contentPane.add(scrollPaneRts);
		
		tableRts = new JTable();
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
		empCB.setBounds(85, 48, 197, 20);
		contentPane.add(empCB);
						
		destCB = new JComboBox<String>();
		destCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		destCB.setBounds(597, 48, 89, 20);
		contentPane.add(destCB);
				
		baseCB = new JComboBox<String>();
		baseCB.setModel(new DefaultComboBoxModel<String>(new String[] {"All"}));
		baseCB.setBounds(691, 48, 96, 20);
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
		
		btnBringSelectedBack = new JButton("Bring selected back");
		btnBringSelectedBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.bringFromArchive();
			}
		});
		btnBringSelectedBack.setBounds(10, 271, 190, 23);
		contentPane.add(btnBringSelectedBack);
		
		label = new JLabel("Begin");
		label.setBounds(293, 28, 46, 14);
		contentPane.add(label);
		
		label_1 = new JLabel("End");
		label_1.setBounds(292, 46, 46, 14);
		contentPane.add(label_1);
		
		label_2 = new JLabel("Begin");
		label_2.setBounds(456, 27, 46, 14);
		contentPane.add(label_2);
		
		label_3 = new JLabel("End");
		label_3.setBounds(456, 45, 46, 14);
		contentPane.add(label_3);

	}
	
	
}
