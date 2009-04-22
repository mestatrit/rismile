package com.risetek.scada.client;

import com.risetek.scada.client.sink.Sink;
import com.risetek.scada.client.view.SystemHomeView;
import com.risetek.scada.client.view.dataPointsView;

public class dataPointsSink extends Sink {
	public static final String Tag = "dataPoints";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "dataPoints", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new dataPointsSink();
			}
		};
	}
	public dataPointsSink(){
		initWidget(new dataPointsView());
	}
}
