package com.risetek.rismile.system.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.system.client.control.SystemAllController;

public class RisetekSystemSink  extends Sink {
	public static final String Tag = "SysInfo";
	
	public SystemAllController systemAllController = new SystemAllController();

	public static SinkInfo init() {
		return new SinkInfo(Tag, "系统配置", "系统配置信息.") {
			public Sink createInstance() {
				return new RisetekSystemSink();
			}
		};
	}

	public RisetekSystemSink() {
		initWidget(systemAllController.view);
		//initTable();
	}
	
}
