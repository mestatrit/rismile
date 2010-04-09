package risetek.client.view;

import risetek.client.control.RadiusConfController;
import risetek.client.model.RadiusConfModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;

public class RadiusConfigView extends Composite {
	interface ProudceUiBinder extends UiBinder<Widget, RadiusConfigView> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	@UiField
	SpanElement	authPort;
	@UiField
	SpanElement	acctPort;
	@UiField
	SpanElement	secretPort;
	@UiField
	SpanElement	versionLabel;
	@UiField
	SpanElement	versionNoteLabel;

	@UiField
	Button authButton;
	
	@UiField
	Button acctButton;
	
	@UiField
	Button secretButton;
	
	/*
	@UiField
	Button licenseButton;
	*/
	
	private RadiusConfController control;
	
	public RadiusConfigView( RadiusConfController control) {
		this.control = control;
		Widget w = uiBinder.createAndBindUi(this);
		w.setHeight(Entry.SinkHeight);
		initWidget(w);
		
		authButton.addClickHandler(control.new authModifyClickListen());
		acctButton.addClickHandler(control.new acctModifyClickListen());
		secretButton.addClickHandler(control.new secretModifyClickListen());
	}


	public void render( RadiusConfModel data )
	{
		authPort.setInnerText(data.getAuthPort());
		acctPort.setInnerText(data.getAcctPort());
		secretPort.setInnerText(data.getSecretKey());
		versionLabel.setInnerText(data.getVersion());
		versionNoteLabel.setInnerText(data.getMaxUser());
	}
	
	protected void onLoad() {
		//请求的数据不能为空，
		control.load();
	}

}
