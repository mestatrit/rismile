package com.risetek.rismile.log.client;

import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.log.client.control.RismileLogController;

public class RismileLogSink extends Sink {
	public static final String Tag = "Log";

	public static SinkInfo init() {
		return new SinkInfo(Tag, "运行记录", "系统运行记录."){
		      @Override
			public Sink createInstance() {
		          return new RismileLogSink();
		        }
		};
	}

	public RismileLogSink() {
		initWidget(RismileLogController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return RismileLogController.INSTANCE;
	}
}
