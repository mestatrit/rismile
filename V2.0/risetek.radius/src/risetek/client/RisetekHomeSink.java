package risetek.client;

import risetek.client.control.ProduceHomeController;

import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.sink.Sink;


public class RisetekHomeSink extends Sink {
	public static final String Tag = "Home";
	
	ProduceHomeController control = new ProduceHomeController();
	
	public static SinkInfo init() {
		if(Entry.OEMFlag == Entry.OEM.risetek )
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
		initWidget(control.view);
	}
}
