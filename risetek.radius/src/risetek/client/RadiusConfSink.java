package risetek.client;

import risetek.client.control.RadiusConfController;

import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;


public class RadiusConfSink extends Sink {
	public static final String Tag = "RadiusConf";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "认证配置", "radius配置信息") {
			public Sink createInstance() {
				return new RadiusConfSink();
			}
		};
	}

	public RadiusConfSink() {
		initWidget(RadiusConfController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return RadiusConfController.INSTANCE;
	}
}
