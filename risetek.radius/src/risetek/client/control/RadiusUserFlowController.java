package risetek.client.control;

import risetek.client.model.RismileUserFlowTable;
import risetek.client.view.FlowView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.dialog.FilterDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.NavBar.NavEvent;
import com.risetek.rismile.client.view.NavBar.NavHandler;

public class RadiusUserFlowController extends AController {
	
	public static RadiusUserFlowController INSTANCE = new RadiusUserFlowController();
	final RismileUserFlowTable data = new RismileUserFlowTable(true);
	private static RequestFactory remoteRequest = new RequestFactory("flow/");
	private final static String emptyForm = "delete/";
	
	public final FlowView view = new FlowView();

	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取流量数据失败");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得流量数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}	
	private RadiusUserFlowController() {

		data.setLimit( view.grid.getRowCount() - 1 );
		view.navbar.addNavHandler(new NavHandler(){
			@Override
			public void onNav(NavEvent event) {
				data.moveDir(event.getResult());
				load();
			}
		});
	}

	public static void load() {
		MessageConsole.setText("提取流量数据");
		String RESTful = INSTANCE.data.getLimit()+"/"+INSTANCE.data.getOffset();
		if( !"".equalsIgnoreCase(INSTANCE.data.filter)) {
			RESTful += "/" + INSTANCE.data.filter;
		}
		remoteRequest.get("info/" + RESTful , null, RemoteCaller);
	}

	@Override
	public void disablePrivate() {
		view.disablePrivate();
	}

	@Override
	public void enablePrivate() {
		view.enablePrivate();
	}

	@Override
	public IRisetekView getView() {
		return view;
	}

	@Override
	public void doAction(int keyCode, boolean alt) {
		view.ProcessControlKey(keyCode, alt);
	}

	// ----------------- 设定用户信息过滤
	public static class FilterFlowAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			control control = new control();
			control.dialog.submit.setText("确定");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class control implements ClickHandler {
			public FilterDialog dialog = new FilterDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					INSTANCE.data.filter = dialog.filter.getText();
					// 查询条件变更，需要归位。
					INSTANCE.data.setOffset(0);
					dialog.hide();
					if(!("".equalsIgnoreCase(INSTANCE.data.filter)))
						INSTANCE.view.setBannerTips("信息被过滤，只显示了满足匹配条件的信息。");
					else
						INSTANCE.view.setBannerTips("");
					load();
				}
			}
		}
	}

	public static class ClearFlowAction implements ClickHandler , RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			RemoteCaller.onError(request, exception);
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			INSTANCE.view.clearButton.setEnabled(true);
			load();
		}

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除流量记录?")) {
				INSTANCE.view.clearButton.setEnabled(false);
				remoteRequest.DELETE(emptyForm, null, ClearFlowAction.this);
			}
		}
	}
}
