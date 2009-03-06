package com.risetek.rismile.system.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.system.client.view.SystemHomeView;


public class RisetekHomeSink extends Sink {
	public static final String Tag = "Home";
	public static SinkInfo init() {
		return new SinkInfo(Tag, "欢迎", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new RisetekHomeSink();
			}
		};
	}
	public RisetekHomeSink(){
		initWidget(new SystemHomeView());
	}
	public void onShow() {
		
	}
	
}
