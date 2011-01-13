package risetek.client;

import risetek.client.control.RadiusUserController;

import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;


public class RadiusUserSink extends Sink {
	public static final String Tag = "UserInfo";

	public static SinkInfo init() {
		return new SinkInfo(Tag, "用户管理", "用户管理（绿底表明用户在线）"){
		      @Override
			public Sink createInstance() {
		          return new RadiusUserSink();
		        }
		};
	}
	
	public RadiusUserSink() {
		initWidget(RadiusUserController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return RadiusUserController.INSTANCE;
	}
}
