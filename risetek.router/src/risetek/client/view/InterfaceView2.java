package risetek.client.view;

import risetek.client.control.IfController;
import risetek.client.model.IfModel;
import risetek.client.view.stick.BundView;
import risetek.client.view.stick.IPCPView;
import risetek.client.view.stick.IfaceView;
import risetek.client.view.stick.LCPView;
import risetek.client.view.stick.LinkView;
import risetek.client.view.stick.RouterView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.view.IView;

public class InterfaceView2 extends Composite implements IView {

	// final Button addIfButton = new Button();
	// final StackPanel ifStackPanel = new StackPanel();
	// final Label infoLabel = new Label("还没有配置接口。");

	// final RadioButton nocompressRadioButton = new
	// RadioButton("compress-group");

	IfController control;

	LCPView lcpView;
	LinkView linkView;
	IfaceView ifaceView;
	RouterView routerView;
	final VerticalPanel outerPanel = new VerticalPanel();

	public InterfaceView2(IfController control) {

		this.control = control;

		outerPanel.setStyleName("if-layout");
		outerPanel.setWidth("100%");
		outerPanel.setBorderWidth(1);
		outerPanel.setHeight(Entry.SinkHeight);
		initWidget(outerPanel);
		//outerPanel.setHeight("100%");

		lcpView = new LCPView(outerPanel, control);

		linkView = new LinkView(outerPanel);

		ifaceView = new IfaceView(outerPanel);

		new BundView(outerPanel);

		new IPCPView(outerPanel);

		routerView = new RouterView(outerPanel);
	}

	//private int nextHeaderIndex = 0;

	// private static final Images images = (Images) GWT.create(Images.class);

	protected void onLoad() {
		control.getIf();
	}

	public int getHeight(){
		return outerPanel.getOffsetHeight();
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
	public void render(IfModel data) {
		lcpView.render(data);
		linkView.render(data);
		ifaceView.render(data);
		routerView.render(data);
		
	}

}
