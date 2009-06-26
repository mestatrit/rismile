package com.risetek.pinger;

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

public class RisetekMonitor extends MonitorState {
	public static final ImageIcon LOGO_ICON = new ImageIcon(
			RisetekMonitor.class.getResource("b.png"));
	
	private JFrame frame;
	private JTextArea textArea;
	//private PingerTray pingerTray;
	static final private DateFormat formatTime = new SimpleDateFormat(
	"yy/MM/dd HH:mm:ss");
	final JLabel StatusLabel = new JLabel("设置运行正常");
	final JLabel StartLabel = new JLabel("监控启动时间");
	final JLabel StatusOKStamp = new JLabel("最近应答正常时间");
	final JLabel StatusErrorStamp = new JLabel("最近应答异常时间");

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
		if( line > 200 )
		{
			textArea.setText("");
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
		// frame.setSize(370,300);
		frame.setBounds(0, 0, 374, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setResizable(false);

	
	
		// final JPanel panel = new JPanel(new GridLayout(5,1));
		final JPanel laypanel = new JPanel();
		laypanel.setLayout(null);
		StatusLabel.setBackground(Color.GREEN);
		//StatusLabel.setSize(334,8);
		StatusLabel.setBounds(10, 5, 344, 18);
		laypanel.add(StatusLabel);
		StatusOKStamp.setBounds(10, 25, 344, 18);
		laypanel.add(StatusOKStamp);
		StatusErrorStamp.setBounds(10, 45, 344, 18);
		laypanel.add(StatusErrorStamp);
		StartLabel.setBounds(10, 65, 344, 18);
		laypanel.add(StartLabel);

		final JScrollPane panel = new JScrollPane();
		panel.setBounds(10, 86, 344, 170);
		//frame.add(panel);
		laypanel.add(panel);

		textArea = new JTextArea();
		textArea.setMinimumSize(new Dimension(0, 10));
		textArea.setEditable(false);
		//textArea.setBounds(10, 106, 344, 150);
		panel.setViewportView(textArea);
		laypanel.add(panel);
		frame.add(laypanel);
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

	@Override
	public void setUIStateError() {
		StatusLabel.setText("设备运行不正常");
		StatusLabel.setForeground(Color.RED);
		Date date = new Date(startTimestamp);
		StartLabel.setText("监控启动时间:" + formatTime.format(date) + "[失败:"+ monitor_error_tick +"次/正常:" + monitor_ok_tick +"次]");
		StatusErrorStamp.setText("最近应答异常时间："+ formatTime.format(new Date(lastError)) );
		
	}

	@Override
	public void setUIStateOK() {
		StatusLabel.setText("设置运行正常");
		StatusLabel.setForeground(Color.GREEN);
		Date date = new Date(startTimestamp);
		StartLabel.setText("监控启动时间:" + formatTime.format(date) + "[失败:"+ monitor_error_tick +"次/正常:" + monitor_ok_tick +"次]");
		StatusOKStamp.setText("最近应答正常时间："+ formatTime.format(new Date(lastOk)) );
	}

}
