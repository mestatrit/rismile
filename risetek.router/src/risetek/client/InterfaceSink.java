package risetek.client;

import risetek.client.view.InterfaceView2;

import com.risetek.rismile.client.sink.Sink;

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
