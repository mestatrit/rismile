package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.dialog.RadiusConfigDialog;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IView;

public class RadiusConfigView extends Composite implements IView{
	final Grid authGrid = new Grid();
	final Grid acctGrid = new Grid();
	final Grid secretGrid = new Grid();
	final Button authButton = new Button();
	final Button acctBotton = new Button();
	final Button secretBotton = new Button();
	final Label versionLabel = new Label("");
	final HTML versionNoteLabel = new HTML("");

	private ClickListener buttonListener = new ModifyClickListener();
	final Label infoLabel = new Label("");
	final FlexTable flexTable = new FlexTable();
	public RadiusConfigView() {

		
		flexTable.setStyleName("page-container");
		initWidget(flexTable);
		flexTable.setWidth("100%");
		flexTable.setStyleName("table");

		flexTable.setWidget(0, 2, infoLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		infoLabel.setStyleName("radius-info-Label");
		flexTable.getFlexCellFormatter().setColSpan(0, 2, 2);

		final HTML authTitleHTML = new HTML("认证端口配置");
		authTitleHTML.setStyleName("radius-table-title");
		flexTable.setWidget(1, 0, authTitleHTML);
		//flexTable.getCellFormatter().setStyleName(1, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 4);

		//final HTML authNoteHTML = new HTML("（早期的RADIUS认证是通过端口号为1645的UDP端口来实现的，它和\"datametrics\"服务相冲突。为RADIUS认证服务正式分配的端口号是1812。）");
		//flexTable.setWidget(2, 1, authNoteHTML);
		//flexTable.getFlexCellFormatter().setColSpan(2, 1, 3);

		flexTable.setWidget(3, 2, authGrid);
		authGrid.setWidth("70%");
		flexTable.getCellFormatter().setWidth(3, 2, "55%");
		authGrid.resize(1, 2);
		authGrid.setText(0, 0, "认证端口");
		authGrid.setStyleName("radius-conf-table");
		authGrid.getCellFormatter().setWidth(0, 0, "60%");
		authGrid.setBorderWidth(1);

		flexTable.setWidget(3, 3, authButton);
		authButton.setStyleName("radius-conf-Button");
		authButton.setText("修改");
		authButton.setWidth("50");
		authButton.addClickListener(buttonListener);

		final HTML hrHtml1 = new HTML("<hr>");
		flexTable.setWidget(4, 1, hrHtml1);
		hrHtml1.setVisible(false);
		flexTable.getFlexCellFormatter().setColSpan(4, 1, 3);

		final HTML acctTitleHTML = new HTML("计费端口配置");
		acctTitleHTML.setStyleName("radius-table-title");
		flexTable.setWidget(5, 0, acctTitleHTML);
		//flexTable.getCellFormatter().setStyleName(5, 0, "radius-table-title");
		flexTable.getFlexCellFormatter().setColSpan(5, 0, 4);

		//final HTML acctNoteHTML = new HTML("（早期的RADIUS计费是通过端口号为1646的UDP端口来实现的，它和\"sa-msg-port\"服务相冲突。为RADIUS计费服务正式分配的端口号是1813。）");
		//flexTable.setWidget(6, 1, acctNoteHTML);
		//flexTable.getFlexCellFormatter().setColSpan(6, 1, 3);

		flexTable.setWidget(7, 2, acctGrid);
		acctGrid.setWidth("70%");
		flexTable.getCellFormatter().setWidth(7, 2, "55%");
		acctGrid.resize(1, 2);
		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setStyleName("radius-conf-table");
		acctGrid.getCellFormatter().setWidth(0, 0, "60%");
		acctGrid.setBorderWidth(1);

		flexTable.setWidget(7, 3, acctBotton);
		acctBotton.setStyleName("radius-conf-Button");
		acctBotton.setText("修改");
		acctBotton.setWidth("50");
		acctBotton.addClickListener(buttonListener);

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
		
		flexTable.setWidget(10, 3, secretBotton);
		secretBotton.setStyleName("radius-conf-Button");
		secretBotton.setText("修改");
		secretBotton.setWidth("50");
		secretBotton.addClickListener(buttonListener);

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

	public RadiusConfController radiusConfController = new RadiusConfController();


	public void setInfo(String info){
		MessageConsole.setText(info);
	}
	
	private class ModifyClickListener implements ClickListener {

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if(sender == authButton){
				new RadiusConfigDialog().show(RadiusConfigView.this, 0, authGrid.getText(0, 1));

			}else if (sender == acctBotton){
				new RadiusConfigDialog().show(RadiusConfigView.this, 1, acctGrid.getText(0, 1));

			}else if (sender == secretBotton){
				new RadiusConfigDialog().show(RadiusConfigView.this, 2, secretGrid.getText(0, 1));

			}
			
		}
		
	}
	public int getHeight(){
		return flexTable.getOffsetHeight();
	}
	public void onResponse(Response response) {
		// TODO Auto-generated method stub
		if(response.getStatusCode() == 200){
			String text = response.getText();
			Document dom = XMLParser.parse(text);
			Element customerElement = dom.getDocumentElement();
			XMLParser.removeWhitespace(customerElement);
			
			NodeList error = customerElement.getElementsByTagName("ERROR");

			if(error.getLength() > 0 && error.item(0).getNodeName().equals("ERROR") ){
				MessageConsole.setText(error.item(0).getFirstChild().getNodeValue());
				return;
			}else{
				MessageConsole.setText("请求数据完毕!");
			}
			String secret = customerElement.getElementsByTagName("secret").item(0).getFirstChild().getNodeValue();
			String auth   = customerElement.getElementsByTagName("auth").item(0).getFirstChild().getNodeValue();
			String acct   = customerElement.getElementsByTagName("acct").item(0).getFirstChild().getNodeValue();
			String serial = customerElement.getElementsByTagName("serial").item(0).getFirstChild().getNodeValue();
			String maxuser = customerElement.getElementsByTagName("maxuser").item(0).getFirstChild().getNodeValue();
			authGrid.setText(0, 1, auth);
			acctGrid.setText(0, 1, acct);
			secretGrid.setText(0, 1, secret);
			versionLabel.setText(serial);
			versionNoteLabel.setText("(授权用户数:"+maxuser+")");
		}else{
			MessageConsole.setText("服务器返回代码:"+response.getStatusCode()+"!");
		}
	}
	protected void onLoad() {
		//请求的数据不能为空，
		
		loadModel();
	}
	public void loadModel() {
		// TODO Auto-generated method stub
		radiusConfController.getConfAll(new LoadAction());
	}
	class LoadAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			RadiusConfModel model = (RadiusConfModel)object;
			authGrid.setText(0, 1, model.getAuthPort());
			acctGrid.setText(0, 1, model.getAcctPort());
			secretGrid.setText(0, 1, model.getSecretKey());
			versionLabel.setText(model.getVersion());
			versionNoteLabel.setText("(授权用户数:"+model.getMaxUser()+")");
		}
		
	}
	
}
