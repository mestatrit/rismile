package risetek.client;

import risetek.client.control.RadiusBlackController;

import com.risetek.rismile.client.sink.Sink;


public class RadiusBlackSink extends Sink {
	public static final String Tag = "BlackInfo";

	public RadiusBlackController control = new RadiusBlackController();
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "不明用户", "不明用户管理"){
		      public Sink createInstance() {
		          return new RadiusBlackSink();
		        }
		};
	}
	
	public RadiusBlackSink() {
		initWidget(control.view);
	}

}
