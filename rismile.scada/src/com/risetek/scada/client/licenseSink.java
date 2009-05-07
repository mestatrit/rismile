package com.risetek.scada.client;

import com.risetek.scada.client.sink.Sink;
import com.risetek.scada.client.view.licenseView;

public class licenseSink extends Sink {
	public static final String Tag = "license";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "license", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new licenseSink();
			}
		};
	}
	public licenseSink(){
		initWidget(new licenseView());
	}
}
