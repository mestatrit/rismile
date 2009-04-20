package com.risetek.pinger;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.jnlp.ServiceManager;
import javax.jnlp.SingleInstanceListener;
import javax.jnlp.SingleInstanceService;
import javax.jnlp.UnavailableServiceException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class RisetekMonitor {
	public static final ImageIcon LOGO_ICON = new ImageIcon(
			RisetekMonitor.class.getResource("b.png"));
	
	private JFrame frame;
	private JTextArea textArea;
	//private PingerTray pingerTray;

	/**
	 * Create the application
	 */
	public RisetekMonitor() {

		try {
			sis = (SingleInstanceService) ServiceManager
					.lookup("javax.jnlp.SingleInstanceService");
			sis.addSingleInstanceListener(sisL);

		} catch (UnavailableServiceException e) {
			sis = null;
		}
		createContents();

		// PingerTray pingerTray = 
		new PingerTray(this);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void log(String message) {
		int line = textArea.getLineCount();
		textArea.append(line + message);
	}

	private static String tagetAddress = "10.0.0.10";
	public String target()
	{
		return tagetAddress;
	}
	/**
	 * Initialize the contents of the frame
	 */
	private void createContents() {
		frame = new JFrame();
		frame.setIconImage(LOGO_ICON.getImage());
		frame.setTitle("Risetek �豸���");
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		final JScrollPane panel = new JScrollPane();
		frame.add(panel);

		textArea = new JTextArea();
		textArea.setMinimumSize(new Dimension(0, 10));
		textArea.setEditable(false);
		panel.setViewportView(textArea);

	}

	/**
	 * Launch the application
	 * 
	 * @param args
	 */

	public static void main(String args[]) {
		// final RisetekMonitor instance = new RisetekMonitor();
		if( args.length > 0)
			tagetAddress = args[0];
		
		for(int l = 0; l < args.length ; l++)
		{
			System.out.println("args: "+ args[l]);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RisetekMonitor window = new RisetekMonitor();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	SingleInstanceService sis;
	SISListener sisL = new SISListener();

	// for proof that only once instance is running.
	private int counter = 0;
	//private boolean keepRunning = true;

	class SISListener implements SingleInstanceListener {
		public void newActivation(String[] params) {
			
			System.out.println("counter[" + (counter++) + "]");
			/*			
			System.out.println("new arguments[" + params[0] + "," + params[1]
					+ "]");
			if (params[1].equals("stop")) {
				keepRunning = false;
			}
*/			
		}
	}

	public void shutdown() {
		sis.removeSingleInstanceListener(sisL);
		System.exit(0);
	}

}