package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.HorizontalTitlePanel;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.ViewComposite;

public class RadiusConfigView extends ViewComposite  implements IRisetekView {
	private final Grid authGrid = new innerGrid();
	private final Grid acctGrid = new innerGrid();
	private final Grid secretGrid = new innerGrid();
	private final Grid versionGrid = new innerGrid();

	final VerticalPanel flexTable = new VerticalPanel();
	
	private final Button authButton = new Button("修改(P)", new RadiusConfController.authModifyClickListen());
	private final Button acctBotton = new Button("修改(M)", new RadiusConfController.acctModifyClickListen());
	private final Button secretBotton = new Button("修改(K)" , new RadiusConfController.secretModifyClickListen());

	/*
	private interface LocalStyle extends CssResource {
	    String enabled();
	    String disabled();
	  }
	LocalStyle localStyle;
	*/
	
	private class innerGrid extends Grid {
		public innerGrid() {
			resize(1,3);
			setHeight("38px");
			setWidth("70%");
			setCellPadding(0);
			setCellSpacing(0);
			
			// TODO: FIXME: 怎么取消对css的依赖呀？
			setStyleName("conf-table");
			setBorderWidth(1);
			
			Style style = getElement().getStyle();
			style.setBackgroundColor("white");
			style.setBorderColor("#999");
			style.setBorderStyle(BorderStyle.SOLID);
			
			getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			getCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			
			style = getCellFormatter().getElement(0,0).getStyle();
			style.setWidth(30, Unit.PCT);
			style.setBorderColor("#999");
			style.setBorderStyle(BorderStyle.SOLID);
			style.setBorderWidth(1, Unit.PX);
			style.setFontWeight(FontWeight.BOLD);
			
			style = getCellFormatter().getElement(0,1).getStyle();
			style.setWidth(30, Unit.PCT);
			style.setBorderColor("#999");
			style.setBorderStyle(BorderStyle.SOLID);
			style.setBorderWidth(1, Unit.PX);
			
			//DOM.setElementAttribute(getElement(), "border-collapse","collapse;");
			
		}
	}
	
	public RadiusConfigView() {

//		Style style;
		
		flexTable.setWidth("100%");
		flexTable.setHeight(Entry.SinkHeight);
		flexTable.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		// 格式调试使用。
		// flexTable.setBorderWidth(1);
		setStyleName("radius-config");
		
		flexTable.add(new HorizontalTitlePanel("认证端口配置", true, UIConfig.TitleHeight));
		flexTable.add(authGrid);

		authGrid.setText(0, 0, "认证端口");
		authGrid.setWidget( 0, 2, authButton);

		flexTable.add(new HorizontalTitlePanel("计费端口配置", false, UIConfig.TitleHeight));
		flexTable.add(acctGrid);

		acctGrid.setText(0, 0, "计费端口");
		acctGrid.setWidget(0, 2, acctBotton);

		flexTable.add(new HorizontalTitlePanel("共享密匙配置", false, UIConfig.TitleHeight));
		flexTable.add(secretGrid);

		secretGrid.setText(0, 0, "共享密匙");
		secretGrid.setWidget(0,2,secretBotton);

		flexTable.add(new HorizontalTitlePanel("产品序列号", false, UIConfig.TitleHeight));
		versionGrid.setText(0, 0, "授权用户数");
		
		flexTable.add(versionGrid);
		
		addLeftSide(flexTable);
		addRightSide(new RadiusConfigSider());
	}
	
	public void render( RadiusConfModel data )
	{
		authGrid.setText(0, 1, data.getAuthPort());
		acctGrid.setText(0, 1, data.getAcctPort());
		secretGrid.setText(0, 1, data.getSecretKey());
		versionGrid.setText(0, 1, data.getMaxUser());
		versionGrid.setText(0, 2, data.getVersion());
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
	public void ProcessControlKey(int keyCode, boolean alt) {
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
	
	private class RadiusConfigSider extends Composite {
		
		private final VerticalPanel spanel = new VerticalPanel();
		
		public RadiusConfigSider() {
			spanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			
			spanel.add(new Stick("info", "认证端口配置必须是与LNS一致的，如果不匹配，无法接收到LNS发送来的认证报文。"));
			spanel.add(new Stick("info", "计费端口配置必须是与LNS一致的，如果不匹配，无法接收到LNS发送来的计费报文。"));
			spanel.add(new Stick("info", "共享密钥配置必须是与LNS一致的，如果不匹配，无法正确解析LNS发送的报文。"));

			spanel.add(new Stick("info", "Alt+（ 快捷键）与鼠标点击按钮具有同样的效果。"));
			spanel.add(new Stick("info", "产品LICENSE需要通过管理员用控制台命令输入。"));

			spanel.setWidth("100%");
			initWidget(spanel);
		}

	}
}
