package risetek.client;

import risetek.client.control.RadiusUserStatusController;

import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;


public class RadiusUserStatusSink extends Sink {
	public static final String Tag = "UserStatusInfo";

	public static SinkInfo init() {
		return new SinkInfo(Tag, "用户状态", "用户运行状态管理（绿底表明用户在线）"){
		      @Override
			public Sink createInstance() {
		          return new RadiusUserStatusSink();
		        }
		};
	}
	
	public RadiusUserStatusSink() {
		initWidget(RadiusUserStatusController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return RadiusUserStatusController.INSTANCE;
	}
}
