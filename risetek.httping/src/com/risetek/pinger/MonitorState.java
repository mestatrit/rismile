
/*
 * ������ά��һ�����״̬�������������ֽ��б��֡�
 */
package com.risetek.pinger;

import java.util.Date;
import java.util.Vector;

public abstract class MonitorState {

	long startTimestamp;
	long lastOk;
	long lastError;
	
	long monitor_tick = 0;
	long monitor_ok_tick = 0;
	long monitor_error_tick = 0;
	
	boolean status_ok_error = true;
	
	public abstract void setUIStateOK();
	public abstract void setUIStateError();
	
	public MonitorState()
	{
		setStateStart();
	}
	
	public void setStateOK()
	{
		lastOk =System.currentTimeMillis();
		monitor_tick++;
		monitor_ok_tick++;
		status_ok_error = true;
		setUIStateOK();
	}
	
	public void setStateError()
	{
		lastError =System.currentTimeMillis();
		monitor_tick++;
		monitor_error_tick++;
		status_ok_error = false;
		setUIStateError();
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
