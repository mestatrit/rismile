package risetek.client;

import risetek.client.control.RadiusUserStatusController;

import com.risetek.rismile.client.sink.Sink;


public class RadiusUserStatusSink extends Sink {
	public static final String Tag = "UserStatusInfo";

	RadiusUserStatusController	control = new RadiusUserStatusController();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "终端状态", "用户状态缩略"){
		      public Sink createInstance() {
		          return new RadiusUserStatusSink();
		        }
		};
	}
	
	public RadiusUserStatusSink() {
		initWidget(control.view);
	}

}
