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

//	@Override
//	public void onModuleLoad() {
//		Window.enableScrolling(true);
//	    DockLayoutPanel outer = getUiBinder();//uiBinder.createAndBindUi(this);
//	    RootLayoutPanel root = RootLayoutPanel.get();
//	    root.add(outer);
//	    RootPanel.get("main").add(outer);
//
//		// Load all the sinks.
//		
//	}
//
//	public void init(){
//		loadSinks();
//		headPanel.add(list);
//
//		DOM.setElementProperty(hbMessage.getElement(), "id", "hbMessage");
//		DOM.setElementProperty(message.getElement(), "id", "message");
//
//		
////		if( Entry.OEMFlag == Entry.OEM.risetek )
////			logo.setUrl(Images.INSTANCE.gwtLogo().getURL());
////		else
////			logo.setUrl(Images.INSTANCE.tongfaLogo().getURL());
//		
//		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
//		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
//		
//		DOM.setStyleAttribute(description.getParent().getElement(), "backGroundColor", "#6694E3");
//
//		History.addValueChangeHandler(new ValueChangeHandler<String>() {
//			public void onValueChange(ValueChangeEvent<String> event) {
//				// Find the SinkInfo associated with the history context. If one
//				// is found, show it (It may not be found, for example, when the
//				// user mis-types a URL, or on startup, when the first context will be "").
//				String token = event.getValue();
//				SinkInfo info = list.find(token);
//				if (info == null) {
//					showInfo();
//					return;
//				}
//				show(info, false);
//			}
//		});
//
//		// Show the initial screen.
//		String initToken = History.getToken();
//		if (initToken.length() > 0) {
//			onHistoryChanged(initToken);
//		} else {
//			showInfo();
//		}
//		// 启动对web服务的poll动作，以确定web是否正确服务。
//		Heartbeat.startHeartbeat();
//
//		login_out.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				load();
//				if( logined ) {
//					logined = false;
//					login_out.setText("特权登录");
//					Window.Location.reload();
//				}
//					
//			}
//			
//		});
//	}
	
	public void loadSinks() {
		Entry.SinkHeight = "500px";
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
		if("NULL".equals(resule)){
			logined = true;
			login_out.setText("退出特权");
			loadPrivateSinks();
		} else {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}
//		MessageConsole.setText(response.getText());
	}
}
