package com.risetek.rismile.system.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.system.client.view.SystemView;

public class RisetekSystemSink  extends Sink {
	public static final String Tag = "SysInfo";
	
	private SystemView systemView = new SystemView();
	public static SinkInfo init() {
		return new SinkInfo(Tag, "系统配置", "系统配置信息.") {
			public Sink createInstance() {
				return new RisetekSystemSink();
			}
		};
	}

	public RisetekSystemSink() {
		initWidget(systemView);
		//initTable();
	}
	
}
