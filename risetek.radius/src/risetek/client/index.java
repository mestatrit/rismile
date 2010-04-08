package risetek.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RisetekSystemSink;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.log.client.RismileLogSink;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class index extends Entry {
	
	public void loadSinks() {
//		Entry.SinkHeight = "500px";  //wangx
		list.addSink(RisetekHomeSink.init());
		list.addSink(RadiusUserStatusSink.init());
		list.addSink(RismileLogSink.init());
	}

	public void loadPrivateSinks() {
		list.addSink(RisetekSystemSink.init());
		list.addSink(RadiusConfSink.init());
		list.addSink(RadiusUserSink.init());
		list.addSink(RadiusBlackSink.init());
	}

	public void onError(Request request, Throwable exception) {
	}

	public void onResponseReceived(Request request, Response response) {
		String resule = XMLDataParse.getElementText(response.getText(), "NONE");
//		if("NULL".equals(resule)){
			logined = true;
			login_out.setText("退出特权");
			loadPrivateSinks();
//		} else {
//			Control control = new Control();
//			control.dialog.confirm.addClickHandler(control);
//			control.dialog.show();
//		}
//		MessageConsole.setText(response.getText());
	}
}
