package com.risetek.scada.client;

import com.risetek.scada.client.sink.Sink;
import com.risetek.scada.client.view.mapsView;

public class mapsSink extends Sink {
	public static final String Tag = "maps";
	
	private final mapsView view = new mapsView();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "maps", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new mapsSink();
			}
		};
	}
	public mapsSink(){
		initWidget(view);
	}

	public void onShow() {
		view.onshow();
	}


}
