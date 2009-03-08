package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IView;

public class RadiusConfigView extends Composite implements IView{
	final Grid authGrid = new Grid();
	final Grid acctGrid = new Grid();
	final Grid secretGrid = new Grid();
	final Label versionLabel = new Label("");
	final HTML versionNoteLabel = new HTML("");

	//private ClickListener buttonListener = new ModifyClickListener();
	final Label infoLabel = new Label("");
	final FlexTable flexTable = new FlexTable();
	
	private RadiusConfController control;
	
	public RadiusConfigView( RadiusConfController control) {
		this.control = control;
		
		flexTable.setStyleName("page-container");
		initWidget(flexTable);
		flexTable.setWidth("100%");
		flexTable.setStyleName("table");

		// 格式调试使用。
		flexTable.setBorderWidth(1);
		
		flexTable.setWidget(0, 2, infoLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		infoLabel.setStyleName("radius-info-Label");
		flexTable.getFlexCellFormatter().setColSpan(0, 2, 2);

		final HTML authTitleHTML = new HTML("认证端口配置");
		authTitleHTML.setStyleName("radius-table-title");
		flexTable.setWidget(1, 0, authTitleHTML);
		//flexTable.getCellFormatter().setStyleName(1, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 4);

		flexTable.setWidget(3, 2, authGrid);
		authGrid.setWidth("70%");
		flexTable.getCellFormatter().setWidth(3, 2, "55%");
		authGrid.resize(1, 2);
		authGrid.setText(0, 0, "认证端口");
		authGrid.setStyleName("radius-conf-table");
		authGrid.getCellFormatter().setWidth(0, 0, "60%");
		authGrid.setBorderWidth(1);

		
		final Button authButton = new Button("修改", control.new authModifyClickListen());

		flexTable.setWidget(3, 3, authButton);
		authButton.setStyleName("radius-conf-Button");
		//authButton.setText("修改");
		authButton.setWidth("50");
		//authButton.addClickListener(buttonListener);

		final HTML hrHtml1 = new HTML("<hr>");
		flexTable.setWidget(4, 1, hrHtml1);
		hrHtml1.setVisible(false);
		flexTable.getFlexCellFormatter().setColSpan(4, 1, 3);

		final HTML acctTitleHTML = new HTML("计费端口配置");
		acctTitleHTML.setStyleName("radius-table-title");
		flexTable.setWidget(5, 0, acctTitleHTML);
		//flexTable.getCellFormatter().setStyleName(5, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(5, 0, 4);

		flexTable.setWidget(7, 2, acctGrid);
		acctGrid.setWidth("70%");
		flexTable.getCellFormatter().setWidth(7, 2, "55%");
		acctGrid.resize(1, 2);
		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setStyleName("radius-conf-table");
		acctGrid.getCellFormatter().setWidth(0, 0, "60%");
		acctGrid.setBorderWidth(1);

		final Button acctBotton = new Button("修改", control.new acctModifyClickListen());

		flexTable.setWidget(7, 3, acctBotton);
		acctBotton.setStyleName("radius-conf-Button");
		//acctBotton.setText("修改");
		acctBotton.setWidth("50");
		//acctBotton.addClickListener(buttonListener);

		final HTML hrHtml2 = new HTML("<hr>");
		flexTable.setWidget(8, 1, hrHtml2);
		hrHtml2.setVisible(false);
		flexTable.getFlexCellFormatter().setColSpan(8, 1, 3);

		final HTML secretTitleHTML = new HTML("共享密匙配置");
		secretTitleHTML.setStyleName("radius-table-title");
		flexTable.setWidget(9, 0, secretTitleHTML);
		//flexTable.getCellFormatter().setStyleName(9, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(9, 0, 4);

		flexTable.setWidget(10, 2, secretGrid);
		secretGrid.setWidth("70%");
		flexTable.getCellFormatter().setWidth(10, 2, "55%");
		secretGrid.resize(1, 2);
		secretGrid.setText(0, 0, "共享密匙");
		secretGrid.setStyleName("radius-conf-table");
		secretGrid.getCellFormatter().setWidth(0, 0, "60%");
		secretGrid.setBorderWidth(1);
		
		final Button secretBotton = new Button("修改" , control.new secretModifyClickListen());
		
		flexTable.setWidget(10, 3, secretBotton);
		secretBotton.setStyleName("radius-conf-Button");
		//secretBotton.setText("修改");
		secretBotton.setWidth("50");
		//secretBotton.addClickListener(buttonListener);

		final HTML hrHtml3 = new HTML("<hr>");
		flexTable.setWidget(11, 1, hrHtml3);
		hrHtml3.setVisible(false);
		flexTable.getFlexCellFormatter().setColSpan(11, 1, 3);


		final HTML versionTileHTML = new HTML("产品序列号");
		versionTileHTML.setStyleName("radius-table-title");
		flexTable.setWidget(12, 0, versionTileHTML);
		//flexTable.getCellFormatter().setStyleName(12, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(12, 0, 4);

		flexTable.setWidget(13, 2, versionLabel);
		versionLabel.setStyleName("radius-conf-version");

		flexTable.setWidget(13, 3, versionNoteLabel);
		versionNoteLabel.setStyleName("radius-conf-version");
		
		final HTML emptyHtml1 = new HTML("&nbsp;");
		flexTable.setWidget(13, 0, emptyHtml1);
		flexTable.getCellFormatter().setWidth(13, 0, "1em");
		
		final HTML emptyHtml2 = new HTML("&nbsp;");
		flexTable.setWidget(13, 1, emptyHtml2);
		flexTable.getCellFormatter().setWidth(13, 1, "2em");
		
		final HTML emptyHtml3 = new HTML("&nbsp;");
		flexTable.setWidget(14, 0, emptyHtml3);
		
		final HTML emptyHtml4 = new HTML("&nbsp;");
		flexTable.setWidget(0, 0, emptyHtml4);

	}

	public void setInfo(String info){
		MessageConsole.setText(info);
	}
	
	public int getHeight(){
		return flexTable.getOffsetHeight();
	}
	public void render( RadiusConfModel data )
	{
		authGrid.setText(0, 1, data.getAuthPort());
		acctGrid.setText(0, 1, data.getAcctPort());
		secretGrid.setText(0, 1, data.getSecretKey());
		versionLabel.setText(data.getVersion());
		versionNoteLabel.setText("(授权用户数:"+ data.getMaxUser() +")");
		
	}
	
	protected void onLoad() {
		//请求的数据不能为空，
		control.getConfAll();
	}
	public void loadModel() {
	}
	class LoadAction extends ViewAction{

		public void onSuccess(Object object) {
			super.onSuccess();
			RadiusConfModel model = (RadiusConfModel)object;
			authGrid.setText(0, 1, model.getAuthPort());
			acctGrid.setText(0, 1, model.getAcctPort());
			secretGrid.setText(0, 1, model.getSecretKey());
			versionLabel.setText(model.getVersion());
			versionNoteLabel.setText("(授权用户数:"+model.getMaxUser()+")");
		}
		
	}
	
	/*
	 * 将自己背景单元（maskPanel ）设定为半透明状态。
	 */
	Element mask = DOM.createDiv();
	public void mask()
	{
		Element maskElement = DOM.getElementById("mask");
		if(maskElement != null)
		{
			DOM.appendChild(maskElement, mask);
			DOM.setIntStyleAttribute(mask, "height", getHeight());
			DOM.setElementProperty(mask, "className", "rismile-mask");
		}
		
	}
	/*
	 * 将自己灰色屏蔽。
	 */
	public void unmask()
	{
		Element maskElement = DOM.getElementById("mask");
		if(maskElement != null){
			DOM.removeChild(maskElement, mask);
		}
	}
}
