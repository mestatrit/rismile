package com.risetek.rismile.log.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.log.client.control.RismileLogController;

public class RismileLogSink extends Sink {
	public static final String Tag = "Log";

	RismileLogController control = new RismileLogController();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "运行记录", "系统运行记录."){
		      public Sink createInstance() {
		          return new RismileLogSink();
		        }
		};
	}

	public RismileLogSink() {
		initWidget(control.view);
	}

	public void onShow() {
		control.view.start_refresh();
	}
	
	public void onHide(){
		control.view.stop_refresh();
	}
}
