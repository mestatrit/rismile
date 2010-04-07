package risetek.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.FlowPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RisetekSystemSink;
import com.risetek.rismile.client.utils.XMLDataParse;
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
		FlowPanel btn = new FlowPanel();
		btn.add(enable);
		btn.setStyleName("enablePanel");
		list.addWidget(btn);
	}

	@Override
	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		String resule = XMLDataParse.getElementText(response.getText(), "NONE");
		if("NULL".equals(resule)){
			login = true;
			enable.setText("ÍË³öÌØÈ¨");
		} else {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}
	}
}
