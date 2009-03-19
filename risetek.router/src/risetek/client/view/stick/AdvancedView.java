package risetek.client.view.stick;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;

public class AdvancedView {

	public AdvancedView(FlexTable flexTable)
	{
		
		//chapmdCheckBox.setText("CHAP认证");
		
		//authGrid.setWidget(1,1, eapCheckBox);
		//eapCheckBox.setText("eap");

		//authGrid.setWidget(2,0, mschapCheckBox);
		//mschapCheckBox.setText("ms-chap");
		
		
		
		final Grid pppGrid = new Grid(3,3);
		pppGrid.setBorderWidth(1);
		pppGrid.setWidth("100%");
	
		pppGrid.setStyleName("if-Grid");
		pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "40%");
		pppGrid.getColumnFormatter().setWidth(1, "40%");
		pppGrid.getColumnFormatter().setWidth(2, "20%");
		pppGrid.setText(0, 0, "用户名:");
		//pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setWidget(0, 2, new Button("修改"));
		
		pppGrid.setText(1, 0, "密码:");
		//pppGrid.setWidget(1, 1, passwordLabel);
		pppGrid.setWidget(1, 2, new Button("修改"));

		pppGrid.setText(2, 0, "MTU:");
		//pppGrid.setWidget(2, 1, mtuLabel);
		pppGrid.setWidget(2, 2, new Button("修改"));
		
		flexTable.setWidget(0, 0, pppGrid);
	}
	
}
