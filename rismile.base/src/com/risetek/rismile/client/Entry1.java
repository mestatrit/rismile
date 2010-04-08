package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry.OEM;
import com.risetek.rismile.client.dialog.AdminDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.MessageConsole;

public abstract class Entry1 implements EntryPoint, RequestCallback {

	public enum OEM
	{
		risetek,
		tongfa
	};
	
	public static OEM OEMFlag = OEM.risetek;
	
	private RequestFactory remoteRequest = new RequestFactory();
	private String checkPath = "penable";
	
	@UiField	public SinkList list;

	private SinkInfo curInfo;
	private Sink curSink;
	// 操作提示
	@UiField
	protected	HTML description;
	
//	@UiField	Image logo;

	@UiField	HorizontalPanel descriptionPanel;

	@UiField	VerticalPanel sinkContainer;
	
	@UiField
	protected	FlowPanel maskPanel;
	
	@UiField
	protected	FlowPanel headPanel;

	@UiField
	protected	HTML hbMessage;

	@UiField
	protected	HTML message;
	
	@UiField	public Button login_out;
	public static boolean logined = false;

	/**
	 * 载入各个sinks的实例，这与每个应用相关，所以在这里用abstract声明，具体载入的实例在其实现类里面去完成。
	 */
	abstract public void loadSinks();
	abstract public void loadPrivateSinks();
	
	public void onHistoryChanged(String token) {
		// Find the SinkInfo associated with the history context. If one is
		// found, show it (It may not be found, for example, when the user mis-
		// types a URL, or on startup, when the first context will be "").
		SinkInfo info = list.find(token);
		if (info == null) {
			showInfo();
			return;
		}
		show(info, false);
	}
	
	public void onModuleLoad() {
		
	}
	
	public void show(SinkInfo info, boolean affectHistory) {
		
	}
	
	// 首先显示Home界面。
	// TODO: Home界面与每个产品相关，是否/怎样实现一个abstract来强制产品实例类来构造实际的Home页面呢？
	// 目前采用各个项目自己构造自己的Home界面来实现这个要求。by ychun.w
	public void showInfo() {
		show(list.find("Home"), false);
	}
	
	public void load() {
		MessageConsole.setText("提取系统配置");
		remoteRequest.get(checkPath, null, this);
	}
	
	public class Control implements ClickHandler, RequestCallback {
		public AdminDialog dialog = new AdminDialog(AdminDialog.LOGIN);

		public void onClick(ClickEvent event) {
			if( dialog.isValid())
			{
				login(dialog.pwdBox.getText(), this);
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			if( dialog.processResponse(response)){
				logined = true;
				login_out.setText("退出特权");
				loadPrivateSinks();
			}
				load();
		}
		
		public void login(String password, RequestCallback callback) {
			String requestData = "password=" + password;
			remoteRequest.get(checkPath, requestData, callback);

		}
	}
}
