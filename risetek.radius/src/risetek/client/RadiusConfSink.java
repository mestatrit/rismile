package risetek.client;

import risetek.client.view.RadiusConfigView;

import com.risetek.rismile.client.sink.Sink;


public class RadiusConfSink extends Sink {
	public static final String Tag = "RadiusConf";
	
	private RadiusConfigView radiusConfigView = new RadiusConfigView();
	public static SinkInfo init() {
		return new SinkInfo(Tag, "认证配置", "radius配置信息.") {
			public Sink createInstance() {
				return new RadiusConfSink();
			}
		};
	}

	public RadiusConfSink() {
		initWidget(radiusConfigView);
	}
	
	public void onShow() {
	}

}
