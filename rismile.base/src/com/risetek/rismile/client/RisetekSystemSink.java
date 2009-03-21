package com.risetek.rismile.client;

import com.risetek.rismile.client.control.SystemController;
import com.risetek.rismile.client.sink.Sink;

public class RisetekSystemSink  extends Sink {
	public static final String Tag = "SysInfo";
	
	public SystemController systemAllController = new SystemController();

	public static SinkInfo init() {
		return new SinkInfo(Tag, "系统配置", "系统配置信息") {
			public Sink createInstance() {
				return new RisetekSystemSink();
			}
		};
	}

	public RisetekSystemSink() {
		initWidget(systemAllController.view);
	}
	
}
