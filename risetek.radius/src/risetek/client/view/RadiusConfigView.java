package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.risetek.rismile.client.Entry;

public class RadiusConfigView extends Composite {
	final Grid authGrid = new Grid(1,2);
	final Grid acctGrid = new Grid(1,2);
	final Grid secretGrid = new Grid(1,2);
	final Label versionLabel = new Label("");
	final HTML versionNoteLabel = new HTML("");

	final FlexTable flexTable = new FlexTable();
	
	private RadiusConfController control;
	
	public RadiusConfigView( RadiusConfController control) {
		this.control = control;
	
		flexTable.setStyleName("radius-config");
		
		flexTable.setWidth("100%");
		flexTable.setStyleName("table");
		flexTable.setHeight(Entry.SinkHeight);
		initWidget(flexTable);
		// 格式调试使用。
		//flexTable.setBorderWidth(1);
		setStyleName("radius-config");
		
		final HTML authTitleHTML = new HTML("认证端口配置");
		authTitleHTML.setStyleName("table-title");
		flexTable.setWidget(0, 0, authTitleHTML);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);

		flexTable.setWidget(1, 0, authGrid);
		flexTable.getCellFormatter().setWidth(1, 0, "55%");
		authGrid.setWidth("70%");
		authGrid.setText(0, 0, "认证端口");
		authGrid.setStyleName("conf-table");
		authGrid.getCellFormatter().setWidth(0, 0, "60%");
		//authGrid.setBorderWidth(1);
		final Button authButton = new Button("修改", control.new authModifyClickListen());
		authButton.setStyleName("conf-Button");

		flexTable.setWidget(1, 1, authButton);

		final HTML acctTitleHTML = new HTML("计费端口配置");
		acctTitleHTML.setStyleName("table-title");
		flexTable.setWidget(2, 0, acctTitleHTML);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);

		flexTable.setWidget(3, 0, acctGrid);
		acctGrid.setWidth("70%");
		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setStyleName("conf-table");
		acctGrid.getCellFormatter().setWidth(0, 0, "60%");
		//acctGrid.setBorderWidth(1);

		final Button acctBotton = new Button("修改", control.new acctModifyClickListen());

		flexTable.setWidget(3, 1, acctBotton);
		acctBotton.setStyleName("conf-Button");

		final HTML secretTitleHTML = new HTML("共享密匙配置");
		secretTitleHTML.setStyleName("table-title");
		flexTable.setWidget(4, 0, secretTitleHTML);
		flexTable.getFlexCellFormatter().setColSpan(4, 0, 2);

		flexTable.setWidget(5, 0, secretGrid);
		secretGrid.setWidth("70%");
		secretGrid.setText(0, 0, "共享密匙");
		secretGrid.setStyleName("conf-table");
		secretGrid.getCellFormatter().setWidth(0, 0, "60%");
		//secretGrid.setBorderWidth(1);
		
		final Button secretBotton = new Button("修改" , control.new secretModifyClickListen());
		flexTable.setWidget(5, 1, secretBotton);
		secretBotton.setStyleName("conf-Button");

		final HTML versionTileHTML = new HTML("产品序列号");
		versionTileHTML.setStyleName("table-title");
		flexTable.setWidget(6, 0, versionTileHTML);
		flexTable.getFlexCellFormatter().setColSpan(6, 0, 2);

		flexTable.setWidget(7, 0, versionLabel);
		versionLabel.setStyleName("conf-version");

		flexTable.setWidget(7, 1, versionNoteLabel);
		versionNoteLabel.setStyleName("conf-version");
	}


	public void render( RadiusConfModel data )
	{
		authGrid.setText(0, 1, data.getAuthPort());
		acctGrid.setText(0, 1, data.getAcctPort());
		secretGrid.setText(0, 1, data.getSecretKey());
		versionLabel.setText(data.getVersion());
		versionNoteLabel.setText("（授权用户数:"+ data.getMaxUser() +"）");
		
	}
	
	protected void onLoad() {
		//请求的数据不能为空，
		control.load();
	}

}
