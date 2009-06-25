
/*
 * 本程序维持一个监控状态，用以其它部分进行表现。
 */
package com.risetek.pinger;

import java.util.Date;
import java.util.Vector;

public class MonitorState {

	long startTimestamp;
	long lastOk;
	long lastError;
	
	long monitor_tick = 0;
	long monitor_ok_tick = 0;
	long monitor_error_tick = 0;
	
	public MonitorState()
	{
		setStateStart();
	}
	
	public void setStateOK()
	{
		lastOk =System.currentTimeMillis();
		monitor_tick++;
		monitor_ok_tick++;
	}
	
	public void setStateError()
	{
		lastError =System.currentTimeMillis();
		monitor_tick++;
		monitor_error_tick++;
	}

	public void setStateStart()
	{
		startTimestamp =System.currentTimeMillis();
	}
	
	class logger {
		String message;
		Date date;
		public logger(String message)
		{
			this.message = message;
			date = new Date(System.currentTimeMillis());
		}
	}

	Vector<logger> logvector = new Vector<logger>();
	
	public void log(String message)
	{
		logger l = new logger(message);
		logvector.add(l);
		
		if( logvector.size() > 120 )
			logvector.remove(0);
	}
}
