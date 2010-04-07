package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;

public class RadiusConfigView extends Composite {
	final Grid authGrid = new Grid(1,3);
	final Grid acctGrid = new Grid(1,3);
	final Grid secretGrid = new Grid(1,3);
	final Grid versionGrid = new Grid(1,2);
	final Label versionLabel = new Label("");
	final HTML versionNoteLabel = new HTML("");

	final VerticalPanel flexTable = new VerticalPanel();
	
	private RadiusConfController control;
	
	public RadiusConfigView( RadiusConfController control) {
		this.control = control;
	
		flexTable.setWidth("100%");
		flexTable.setHeight(Entry.SinkHeight);
		flexTable.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(flexTable);
		// 格式调试使用。
		//flexTable.setBorderWidth(1);
		setStyleName("radius-config");
		
		final HTML authTitleHTML = new HTML("认证端口配置");
		authTitleHTML.setStyleName("title");
		flexTable.add(authTitleHTML);

		flexTable.add(authGrid);
		authGrid.setWidth("70%");
		authGrid.setText(0, 0, "认证端口");
		authGrid.setStyleName("conf-table");
		authGrid.setBorderWidth(1);
		final Button authButton = new Button("修改", control.new authModifyClickListen());
		authButton.setStyleName("conf-Button");
		authGrid.setWidget( 0, 2, authButton);

		final HTML acctTitleHTML = new HTML("计费端口配置");
		acctTitleHTML.setStyleName("title");
		flexTable.add(acctTitleHTML);

		flexTable.add(acctGrid);
		acctGrid.setWidth("70%");
		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setStyleName("conf-table");

		final Button acctBotton = new Button("修改", control.new acctModifyClickListen());
		acctBotton.setStyleName("conf-Button");
		acctGrid.setWidget(0, 2, acctBotton);

		final HTML secretTitleHTML = new HTML("共享密匙配置");
		secretTitleHTML.setStyleName("title");
		flexTable.add(secretTitleHTML);

		flexTable.add(secretGrid);
		secretGrid.setWidth("70%");
		secretGrid.setText(0, 0, "共享密匙");
		secretGrid.setStyleName("conf-table");
		
		final Button secretBotton = new Button("修改" , control.new secretModifyClickListen());
		secretBotton.setStyleName("conf-Button");
		secretGrid.setWidget(0,2,secretBotton);

		final HTML versionTileHTML = new HTML("产品序列号");
		versionTileHTML.setStyleName("title");
		flexTable.add(versionTileHTML);
		
		versionLabel.setStyleName("conf-version");

		versionNoteLabel.setStyleName("conf-version");

		versionGrid.setWidget(0,0,versionLabel);
		versionGrid.setWidget(0,1,versionNoteLabel);
		versionGrid.setWidth("70%");
		versionGrid.setStyleName("conf-table");
		
		flexTable.add(versionGrid);
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
