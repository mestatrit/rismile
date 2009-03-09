package risetek.client;

import risetek.client.control.IfController;

import com.risetek.rismile.client.sink.Sink;

public class InterfaceSink extends Sink{
	public static final String Tag = "Interface";
	
	IfController control = new IfController();
	
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "接口设置", "路由器接口设置") {
			public Sink createInstance() {
				return new InterfaceSink();
			}
		};
	}
	public InterfaceSink(){
		initWidget(control.view);
	}
	
	public void onShow() {
		//control.getIf();
	}
}
