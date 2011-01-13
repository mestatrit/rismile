package risetek.client;

import risetek.client.control.RadiusUserFlowController;

import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;


public class RadiusUserFlowSink extends Sink {
	public static final String Tag = "UserFlowInfo";

	public static SinkInfo init() {
		return new SinkInfo(Tag, "流量记录", "流量记录管理")
		{
		      @Override
			public Sink createInstance() {
		          return new RadiusUserFlowSink();
		        }
		};
	}
	
	public RadiusUserFlowSink() {
		initWidget(RadiusUserFlowController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return RadiusUserFlowController.INSTANCE;
	}
}
