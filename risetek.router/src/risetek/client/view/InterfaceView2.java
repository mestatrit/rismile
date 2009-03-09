package risetek.client.view;

import risetek.client.control.IfController;
import risetek.client.model.IfModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.view.IView;

/*
 * 配置分别存在如下大项：
 * LCP: username password auth_method
 * 
 */


public class InterfaceView2 extends Composite implements IView{
	final Button addIfButton = new Button();
	final StackPanel ifStackPanel = new StackPanel();
	final Label infoLabel = new Label("还没有配置接口。");
	
	//final RadioButton nocompressRadioButton = new RadioButton("compress-group");
	
	IfController control;
	
	LCPView lcpView;
	
	public InterfaceView2(IfController control) {
		
		this.control = control;
		
		final VerticalPanel outerPanel = new VerticalPanel();
		outerPanel.setStyleName("if-layout");
		outerPanel.setWidth("100%");
		outerPanel.setBorderWidth(1);
		initWidget(outerPanel);
		
		// LCP ------------------------------------------------------
		lcpView = new LCPView(outerPanel);

		// LINK ------------------------------------------------------------
		new LinkView(outerPanel);

		// DIAL-ON-DEMAND -------------------------------------
		// TIMEOUT -----------------------------------------------
		new IfaceView(outerPanel);
		// COMPRESS ---------------------------------------------------
		new BundView(outerPanel);
		// IPCP -----------------------------------------------------------
		new IPCPView(outerPanel);
		// ROUTER -----------------------------------------------------------
		new RouterView(outerPanel);		
	}
	private int nextHeaderIndex = 0;
	//private static final Images images = (Images) GWT.create(Images.class);
	
	protected void onLoad(){
		loadModel();
	}
	
	public void loadModel(){
		control.getIf();
	}
	
	public void mask() {
		
	}

	public void unmask() {
		
	}
	
	public void render(IfModel data)
	{
		lcpView.render();
		
	}
	
}
