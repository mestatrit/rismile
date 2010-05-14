package risetek.client;

import risetek.client.control.ProduceHomeController;

import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.sink.Sink;


public class RisetekHomeSink extends Sink {
	public static final String Tag = "Home";
	
	public static SinkInfo init() {
		if(RismileContext.OEMFlag == RismileContext.OEM.risetek )
		return new SinkInfo(Tag, "欢迎", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new RisetekHomeSink();
			}
		};
		else
			return new SinkInfo(Tag, "欢迎", "欢迎使用本公司产品") {
			public Sink createInstance() {
				return new RisetekHomeSink();
			}
		};
			
	}
	public RisetekHomeSink(){
		initWidget(ProduceHomeController.INSTANCE.view);
	}

	@Override
	public AController getController() {
		return ProduceHomeController.INSTANCE;
	}
}
