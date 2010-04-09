package risetek.client.control;

import risetek.client.dialog.UserDateilsInfoDialog;
import risetek.client.dialog.UserFilterDialog;
import risetek.client.model.RismileUserStatusTable;
import risetek.client.view.UserStatusView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.log.client.control.RismileTableController;
import com.risetek.rismile.log.client.model.RismileTable;

public class RadiusUserStatusController extends RismileTableController {

	private static String emptyForm = "clearuser";

	RismileUserStatusTable data = new RismileUserStatusTable();

	public UserStatusView view;
//	public UserStatusDetailsView detailsView;
	private UserDateilsInfoDialog userDateilsInfo;

	public RadiusUserStatusController() {
		view = new UserStatusView(this);
		userDateilsInfo = new UserDateilsInfoDialog(this);
		data.setLimit( view.grid.getRowCount() );
	}

	public void load() {
		MessageConsole.setText("提取用户数据");
		String query = "lpage=" + data.getLimit() + "&offset="
				+ data.getOffset() + "&like=" +data.filter ;
		remoteRequest.get("SqlUserInfoXML", query, this);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取用户状态数据失败");
	}

	public void onResponseReceived(Request request, Response response)
	{
		MessageConsole.setText("获得用户状态数据");
		data.parseXML(response.getText());
		view.render(data);
	}

	public class TableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			String tisp_value = table.getText(row, col);
			int index = row * UserStatusView.colCount + col;
			String[][] d = data.getData();
			if(index >= d.length){
				return;
			}
			openList(d[index]);
			if( tisp_value != null )
			{
//				if (Window.confirm("是否要删除该用户?\n" + "用户名:"
//						+ tisp_value + "\n") ) {
//				}
				
			}
		}
	}

	private void openList(String[] data){
		DialogBox dialog = new DialogBox();
		dialog.setGlassEnabled(true);
//		detailsView.setTitle(data[0]);
//		if(data!=null){
//			userDateils.render(data);
//		}
		dialog.add(userDateilsInfo);
		userDateilsInfo.render(data);
		dialog.setText("端详细内容");
		int left = (Window.getClientWidth() - 417) >> 1;
		int top = (Window.getClientHeight() - 316) >> 1;
		dialog.setPopupPosition(left, top);
//		dialog.center();
		dialog.show();
	}

	// ----------------- 设定用户信息过滤
	public class FilterUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			control control = new control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class control implements ClickHandler {
			public UserFilterDialog dialog = new UserFilterDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					data.filter = dialog.filter.getText();
					// 查询条件变更，需要归位。
					data.setOffset(0);
					dialog.hide();
					if(!("".equalsIgnoreCase(data.filter)))
						view.setBannerTips("用户信息被过滤");
					else
						view.setBannerTips("");
					load();
				}
			}
		}
	}
	
	// 清除所有用户
	public class EmptyAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("清除用户操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除所有用户?")) {
				remoteRequest.get(emptyForm, null, new EmptyCallback());
			}
		}
	}
	
	public RismileTable getTable() {
		return data;
	}

	public UserStatusView getView() {
		return view;
	}

	protected RequestFactory remoteRequest = new RequestFactory();
	
	// NAVIGATOR 按键的事件处理
	public class navigatorFirstClick implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			int offset;
			if( getTable().ASC )
				offset = 0;
			else
				offset = getTable().getSum() - getTable().getLimit();
				
			getTable().setOffset(offset);
			load();
		}
	}
	
	public class navigatorNextClick implements ClickHandler
	{

		public void onClick(ClickEvent event) {
			int offset;
			if( !getTable().ASC )
				offset = getTable().getOffset() - getTable().getLimit();
			else
				offset = getTable().getOffset() + getTable().getLimit();
			getTable().setOffset(offset);
			load();
		}
		
	}

	public class navigatorPrevClick implements ClickHandler
	{
		public void onClick(ClickEvent event) {
			int offset;
			if( getTable().ASC )
				offset = getTable().getOffset() - getTable().getLimit();
			else
				offset = getTable().getOffset() + getTable().getLimit();

			getTable().setOffset(offset);
			load();
		}
	}
	
	public class navigatorLastLastClick implements ClickHandler
	{
		public void onClick(ClickEvent event) {
			int offset;
			if( !getTable().ASC )
				offset = 0;
			else
				offset = getTable().getSum() - getTable().getLimit();
				
			getTable().setOffset(offset);
			load();
		}
		
	}

}
