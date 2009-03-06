package com.risetek.rismile.log.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.log.client.view.RismileLogView;

public class RismileLogSink extends Sink {
	public static final String Tag = "Log";

	String[] columns = {"序号","日期时间","运行记录"};
	String[] columnStyles = {"id","datetime","record"};
	int rowCount = 20;	
	
	RismileLogView logView = new RismileLogView(columns, columnStyles, rowCount);
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "记录", "系统运行记录."){
		      public Sink createInstance() {
		          return new RismileLogSink();
		        }
		};
	}

	public RismileLogSink() {
		initWidget(logView);
	}

	public void onShow() {
		logView.start_refresh();
	}
	
	public void onHide(){
		logView.stop_refresh();
	}

}
