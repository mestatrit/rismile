package risetek.client;

import risetek.client.view.SystemHomeView;

import com.risetek.rismile.client.sink.Sink;


public class RisetekHomeSink extends Sink {
	public static final String Tag = "Home";
	public static SinkInfo init() {
		return new SinkInfo(Tag, "欢迎", "欢迎使用成都中联信通科技有限公司产品") {
			public Sink createInstance() {
				return new RisetekHomeSink();
			}
		};
	}
	public RisetekHomeSink(){
		initWidget(new SystemHomeView());
	}
}
