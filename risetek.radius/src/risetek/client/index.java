package risetek.client;

import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RisetekSystemSink;
import com.risetek.rismile.log.client.RismileLogSink;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class index extends  Entry{

	public void loadSinks() {
		Entry.SinkHeight = "500px";
		list.addSink(RisetekHomeSink.init());
		list.addSink(RisetekSystemSink.init());
		list.addSink(RadiusConfSink.init());
		list.addSink(RadiusUserSink.init());
		list.addSink(RadiusBlackSink.init());
		list.addSink(RismileLogSink.init());
	}
}
