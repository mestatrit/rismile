package com.risetek.router.client;

import com.risetek.rismile.client.sink.Sink;
import com.risetek.router.client.view.InterfaceView2;

public class InterfaceSink extends Sink{
	public static final String Tag = "Interface";
	final InterfaceView2 ifView2 = new InterfaceView2();
	public static SinkInfo init() {
		return new SinkInfo(Tag, "接口设置", "路由器接口设置") {
			public Sink createInstance() {
				return new InterfaceSink();
			}
		};
	}
	public InterfaceSink(){
		initWidget(ifView2);
	}
	public void onShow() {
		
	}
}
