package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserFilterDialog extends CustomDialog {

	public final TextBox filter = new TextBox();
	
	private final FocusPanel focusPanel = new FocusPanel();
	
	public UserFilterDialog(){
		add(new Label("请输入过滤信息："),DockPanel.NORTH);
		
		Grid gridFrame = new Grid(2, 1);
		
		gridFrame.setWidget(1,0,filter);
		
		filter.setTabIndex(1);
		
		focusPanel.add(gridFrame);
		add(focusPanel,DockPanel.CENTER);

		setSize("280","200");
	}
	
	public void show(){
		show("设定信息过滤");
		filter.setFocus(true);
	}

	public boolean isValid()
	{
		String text = filter.getText();
		if ( null == text || "".equals(text))
		{
			setMessage("过滤设定不能为空");
			return false;
		}

		return true;
	}

	@Override
	public Widget getFirstTabIndex() {
		return filter;
	}
}
