package GUI;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TrackViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		displayMe(1);
	}

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
	public TrackViewer(int routeID) {
		setBounds(100, 100, 638, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setBounds(5, 5, 612, 428);
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(new URL("http://c.tile.openstreetmap.org/8/140/87.png"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		contentPane.setLayout(null);
		label.setIcon(icon); 
		contentPane.add(label);
	}

}
