package risetek.client;

import risetek.client.control.IfController;

import com.risetek.rismile.client.sink.Sink;

public class InterfaceSink extends Sink{
	public static String Tag = "Dialer0";
	IfController control;
	
	public static SinkInfo init(final int unit) {
		// Tag += unit;
		return new SinkInfo(Tag , "接口 "+unit+" 设置", "路由器拨号接口设置") {
			public Sink createInstance() {
				return new InterfaceSink(unit);
			}
		};
	}
	public InterfaceSink(int unit){
		control  = new IfController(unit);
		initWidget(control.view);
	}
	
	public void onShow()
	{
		control.load();
	}
	
}
