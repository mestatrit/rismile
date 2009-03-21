package risetek.client;

import risetek.client.control.RadiusUserController;

import com.risetek.rismile.client.sink.Sink;


public class RadiusUserSink extends Sink {
	public static final String Tag = "UserInfo";

	RadiusUserController	control = new RadiusUserController();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "用户", "用户管理"){
		      public Sink createInstance() {
		          return new RadiusUserSink();
		        }
		};
	}
	
	public RadiusUserSink() {
		initWidget(control.view);
	}

}
