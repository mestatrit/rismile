package com.risetek.pinger;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.Timer;


public class PingerTray {
	static final private DateFormat formatTime = new SimpleDateFormat(
			"yy/MM/dd HH:mm:ss");
	private static final int INTERVAL = 5 * 1000;

	private static final ImageIcon CONNECTED_ICON = new ImageIcon(
			PingerTray.class.getResource("g.png"));
	private static final ImageIcon DISCONNECT_ICON = new ImageIcon(
			PingerTray.class.getResource("r.png"));
	
	PopupMenu popup = new PopupMenu();
	MenuItem stopItem = new MenuItem("停止监控");
	MenuItem exitItem = new MenuItem("退出监控");
	MenuItem startItem = new MenuItem("开始监控");
	
	private Timer timer;

	private RisetekMonitor mainWin;
	private TrayIcon trayIcon;

	public PingerTray(RisetekMonitor monitor) {
		this.mainWin = monitor;
		init();
	}

	private void init() {
		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray();

			MouseListener mouseListener = new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					//System.out.println("Tray Icon - Mouse clicked!");
					if (mainWin != null) {
						mainWin.show();
					}
				}

				public void mouseEntered(MouseEvent e) {
					//System.out.println("Tray Icon - Mouse entered!");
				}

				public void mouseExited(MouseEvent e) {
					//System.out.println("Tray Icon - Mouse exited!");
				}

				public void mousePressed(MouseEvent e) {
					//System.out.println("Tray Icon - Mouse pressed!");
				}

				public void mouseReleased(MouseEvent e) {
					//System.out.println("Tray Icon - Mouse released!");
				}
			};

		
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			};

			ActionListener stopListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopItem.setEnabled(false);
					startItem.setEnabled(true);
					timer.stop();
					setImage(RisetekMonitor.LOGO_ICON.getImage());					
				}
			};
			ActionListener startListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopItem.setEnabled(true);
					startItem.setEnabled(false);
					timer.start();
				}
			};

			exitItem.addActionListener(exitListener);
			popup.add(exitItem);

			stopItem.addActionListener(stopListener);
			popup.add(stopItem);

			startItem.addActionListener(startListener);
			popup.add(startItem);

			
			trayIcon = new TrayIcon(RisetekMonitor.LOGO_ICON.getImage(),
					"SwingPinger", popup);
			trayIcon.setToolTip("Risetek监控\r\n系统正常");
			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (mainWin != null) {
						mainWin.show();
					}
				}
			};

			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(actionListener);
			trayIcon.addMouseListener(mouseListener);

			// Depending on which Mustang build you have, you may need to
			// uncomment
			// out the following code to check for an AWTException when you add
			// an image to the system tray.
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}

			timer = new Timer(INTERVAL, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HTTPPing();
				}
			});
			timer.setInitialDelay(0);
			startListener.actionPerformed(null);

		} else {
			System.err.println("System tray is currently not supported.");
		}
	}

	public void setImage(Image image) {
		this.trayIcon.setImage(image);
	}

	public void setTooltip(String tooltip) {
		this.trayIcon.setToolTip(tooltip);
	}

	public void displayMessage(String caption, String text)
	{
		this.trayIcon.displayMessage(caption, text, MessageType.NONE);
	}

	private void HTTPPing() {
		URL target;
		try {
			target = new URL("http://"+mainWin.target()+"/forms/hb");
			try {
				HttpURLConnection connection = (HttpURLConnection) target
						.openConnection();
				connection.setConnectTimeout(1000);
				try {
					connection.connect();
					// System.out.println(connection.getResponseMessage());
					if ("OK".equals(connection.getResponseMessage())) {
						setImage(CONNECTED_ICON.getImage());
						Date date = new Date(System.currentTimeMillis());
						setTooltip("最新监测时间：\r\n" + formatTime.format(date));
						mainWin.log("最新监测时间：" + formatTime.format(date) + "\r\n");
					} else
					{
						setImage(DISCONNECT_ICON.getImage());
						Date date = new Date(System.currentTimeMillis());
						mainWin.log("最新监测时间：" + formatTime.format(date) + "\r\n");
					}
				} catch (IOException e) {
					setImage(DISCONNECT_ICON.getImage());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}
}
