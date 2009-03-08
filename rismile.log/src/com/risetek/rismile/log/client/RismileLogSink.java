package com.risetek.rismile.log.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.log.client.control.RismileLogController;

public class RismileLogSink extends Sink {
	public static final String Tag = "Log";

	RismileLogController control = new RismileLogController();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "记录", "系统运行记录."){
		      public Sink createInstance() {
		          return new RismileLogSink();
		        }
		};
	}

	public RismileLogSink() {
		initWidget(control.logView);
	}

	public void onShow() {
		control.logView.start_refresh();
	}
	
	public void onHide(){
		control.logView.stop_refresh();
	}

}
