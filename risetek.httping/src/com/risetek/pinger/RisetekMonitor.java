package com.risetek.pinger;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jnlp.ServiceManager;
import javax.jnlp.SingleInstanceListener;
import javax.jnlp.SingleInstanceService;
import javax.jnlp.UnavailableServiceException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class RisetekMonitor extends MonitorState {
	public static final ImageIcon LOGO_ICON = new ImageIcon(
			RisetekMonitor.class.getResource("b.png"));
	
	private JFrame frame;
	private JTextArea textArea;
	//private PingerTray pingerTray;
	static final private DateFormat formatTime = new SimpleDateFormat(
	"yy/MM/dd HH:mm:ss");

	
	// public MonitorState State = new MonitorState();
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
		Date date = new Date(System.currentTimeMillis());
		int line = textArea.getLineCount();
		if( line > 20 )
		{
			textArea.removeAll();
		}
		textArea.append("["+formatTime.format(date) + "] " + message);
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
		frame.setTitle("Risetek 设备监控");
		//frame.setBounds(100, 100, 500, 375);
		frame.setSize(370,300);
		//frame.setBounds(0, 0, 374, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		/*		
		final JPanel banner = new JPanel();
		frame.add(banner);
		
		
		final JScrollPane panel = new JScrollPane();
		frame.add(panel);

		textArea = new JTextArea();
		textArea.setMinimumSize(new Dimension(0, 10));
		textArea.setEditable(false);
		panel.setViewportView(textArea);

*/
	
	
	
		final JPanel panel = new JPanel(new GridLayout(5,1));
		// panel.setLayout(null);
		
		frame.add(panel, BorderLayout.CENTER);
		
		final JLabel label = new JLabel("设置运行正常");
		label.setBackground(Color.GREEN);
		label.setSize(334,8);
		//label.setBounds(10, 10, 344, 18);
		panel.add(label);

		final JLabel label_1 = new JLabel("监控启动时间");
		//label_1.setBounds(10, 34, 344, 18);
		panel.add(label_1);

		final JLabel label_2 = new JLabel("最近应答正常时间");
		//label_2.setBounds(10, 58, 344, 18);
		panel.add(label_2);

		final JLabel label_3 = new JLabel("最近应答异常时间");
		//label_3.setBounds(10, 82, 344, 18);
		panel.add(label_3);

		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(Color.black, 1, false));
		textArea.setSize(334, 44);
		//textArea.setBounds(10, 106, 344, 150);
		panel.add(textArea);
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
