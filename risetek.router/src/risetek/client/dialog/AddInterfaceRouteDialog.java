package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class AddInterfaceRouteDialog extends CustomDialog {

	public final TextBox destBox = new TextBox();
	public final TextBox maskBox = new TextBox();
	
	public AddInterfaceRouteDialog(){
		this.setText("添加接口路由");
		VerticalPanel panel = new VerticalPanel();		
		addStyleName("dialog");
		panel.setBorderWidth(1);
		
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		panel.add(new Label("目标地址：",false));
		panel.add(destBox);
		panel.add(new Label("地址掩码：",false));
		panel.add(maskBox);
		
		destBox.setTabIndex(1);
		maskBox.setTabIndex(2);
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show()
	{
		super.show();
		destBox.setFocus(true);
	}
	
	public Widget getFirstTabIndex() {
		return destBox;
	}
	
	public boolean isValid()
	{
		String check = Validity.validIpAddress(destBox.getText());
		
		if( check != null )
			setMessage(check);
		
		check = Validity.validIpAddress(maskBox.getText());
		if( check != null )
			setMessage(check);
		
		return true;
	}
}
