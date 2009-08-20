package com.risetek.scada.server;


import com.google.gwt.user.client.Timer;

public class GPSDatas {
	static private GPSDatas data = new GPSDatas();
	static private int times = 0;
	
	public static GPSDatas getDatas()
	{
//		data.new dataMonitor();
		return data;
	}
	public String Serial()
	{
		times++;
		return "gps "+times; 
	}
	
	public void setDatas()
	{
		synchronized(data)
		{
			try {
				data.notify();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class dataMonitor {
		public dataMonitor() {
			Timer mo = new Timer() {
				public void run() {
					setDatas();
				}
			};
			mo.schedule(2000);
		}
	}
}
