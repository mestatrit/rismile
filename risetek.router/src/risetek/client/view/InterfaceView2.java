package risetek.client.view;

import risetek.client.control.IfController;
import risetek.client.model.IfModel;
import risetek.client.view.stick.BundView;
import risetek.client.view.stick.IPCPView;
import risetek.client.view.stick.IfaceView;
import risetek.client.view.stick.LCPView;
import risetek.client.view.stick.LinkView;
import risetek.client.view.stick.RouterView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;

public class InterfaceView2 extends Composite {

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

		ifaceView = new IfaceView(outerPanel);

		new BundView(outerPanel);

		new IPCPView(outerPanel);

		linkView = new LinkView(outerPanel);

		routerView = new RouterView(outerPanel);
	}

	//private int nextHeaderIndex = 0;

	// private static final Images images = (Images) GWT.create(Images.class);

	protected void onLoad() {
		control.getIf();
	}

	public void render(IfModel data) {
		lcpView.render(data.config);
		linkView.render(data);
		ifaceView.render(data);
		routerView.render(data);
		
	}

}
