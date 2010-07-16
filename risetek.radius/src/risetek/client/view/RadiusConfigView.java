package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.PromptPanel;
import com.risetek.rismile.client.view.IRisetekView;

public class RadiusConfigView extends Composite  implements IRisetekView {
	final Grid authGrid = new Grid(1,3);
	final Grid acctGrid = new Grid(1,3);
	final Grid secretGrid = new Grid(1,3);
	final Grid versionGrid = new Grid(1,2);
	final Label versionLabel = new Label("");
	final HTML versionNoteLabel = new HTML("");

	final VerticalPanel flexTable = new VerticalPanel();
	
	final Button authButton;
	final Button acctBotton;
	final Button secretBotton;
	
	final Grid main = new Grid(1, 2);
	
	public RadiusConfigView() {
		main.setWidth("100%");
		main.setHeight(Entry.SinkHeight);
		main.setCellPadding(0);
		main.setCellSpacing(0);
		main.getCellFormatter().addStyleName(0, 0, "mainLeft");
		initWidget(main);
		
		flexTable.setWidth("100%");
		flexTable.setHeight(Entry.SinkHeight);
		flexTable.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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
		authButton = new Button("修改(P)", new RadiusConfController.authModifyClickListen());
		authButton.addStyleName("conf-Button");
		authGrid.setWidget( 0, 2, authButton);

		final HTML acctTitleHTML = new HTML("计费端口配置");
		acctTitleHTML.setStyleName("title");
		flexTable.add(acctTitleHTML);

		flexTable.add(acctGrid);
		acctGrid.setWidth("70%");
		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setStyleName("conf-table");

		acctBotton = new Button("修改(M)", new RadiusConfController.acctModifyClickListen());
		acctBotton.addStyleName("conf-Button");
		acctGrid.setWidget(0, 2, acctBotton);

		final HTML secretTitleHTML = new HTML("共享密匙配置");
		secretTitleHTML.setStyleName("title");
		flexTable.add(secretTitleHTML);

		flexTable.add(secretGrid);
		secretGrid.setWidth("70%");
		secretGrid.setText(0, 0, "共享密匙");
		secretGrid.setStyleName("conf-table");
		
		secretBotton = new Button("修改(K)" , new RadiusConfController.secretModifyClickListen());
		secretBotton.addStyleName("conf-Button");
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
		
		main.setWidget(0, 0, flexTable);
		main.setWidget(0, 1, initPromptGrid());
		main.getCellFormatter().setWidth(0, 0, "90%");
	}

	private Widget initPromptGrid(){
		Grid promptGrid = new Grid(1, 1);
		promptGrid.setCellPadding(0);
		promptGrid.setCellSpacing(0);
		promptGrid.setHeight(Entry.SinkHeight);
		promptGrid.setWidth("170px");
		promptGrid.setWidget(0, 0, initMessagePanel());
		promptGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		promptGrid.getCellFormatter().setStyleName(0, 0, "prompt-border");
		promptGrid.getCellFormatter().setHeight(0, 0, "100%");
		return promptGrid;
	}

	private Widget initMessagePanel(){
		PromptPanel messagePanel = new PromptPanel();
		messagePanel.setTitleText("操作提示");
		HTML body = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;<strong>认证端口配置：</strong>配置LNS接入端口。" +
				"<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;<strong>计费端口配置：</strong>配置计费服务器接口端口。" +
				"<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;<strong>共享密钥配置：</strong>配置认证过程中的共享密钥。" +
				"<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;<strong>产品序列号：</strong>显示当前设备的序列号以及最大授权用户数目。");
		messagePanel.setBody(body);
		body.setStyleName("prompt-message");
		body.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return messagePanel;
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
		RadiusConfController.load();
	}

	public void disablePrivate() {
		authButton.setEnabled(false);
		acctBotton.setEnabled(false);
		secretBotton.setEnabled(false);
	}

	public void enablePrivate() {
		authButton.setEnabled(true);
		acctBotton.setEnabled(true);
		secretBotton.setEnabled(true);
	}

	@Override
	public void doAction(int keyCode) {
		switch (keyCode) {
		case KEY.P:
			if(authButton.isEnabled()){
				authButton.click();
			}
			break;
		case KEY.M:
			if(acctBotton.isEnabled()){
				acctBotton.click();
			}
			break;
		case KEY.K:
			if(secretBotton.isEnabled()){
				secretBotton.click();
			}
		default:
			break;
		}
	}
	
}
