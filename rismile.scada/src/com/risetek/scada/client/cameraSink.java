package com.risetek.scada.client;

import com.risetek.scada.client.sink.Sink;
import com.risetek.scada.client.view.cameraView;

public class cameraSink extends Sink {
	public static final String Tag = "camera";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "camera", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new cameraSink();
			}
		};
	}
	public cameraSink(){
		initWidget(new cameraView());
	}
}
