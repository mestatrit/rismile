package com.risetek.rismile.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.XMLDataParse;

public class CustomDialog extends DialogBox {

	private static CustomDialogUiBinder uiBinder = GWT
			.create(CustomDialogUiBinder.class);

	interface CustomDialogUiBinder extends UiBinder<Widget, CustomDialog> {
	}

	@UiField protected Label label;

	@UiField protected VerticalPanel mainPanel;
	
	@UiField protected Label message;
	
	@UiField public Button submit;
	
	@UiField Button cancel;

	public CustomDialog() {
		setWidget(uiBinder.createAndBindUi(this)); 
		setGlassEnabled(true);
		//setAnimationEnabled(true);
		submit.setTabIndex(1001);
		cancel.setTabIndex(1002);
		center();
	}

	@Override
	public void onMouseDown(Widget sender, int x, int y) {
		
	}

	protected void setMessage(String msg) {
		message.setText(msg);
	}
	
	@UiHandler("cancel")
	void onClickCancel(ClickEvent e) {
		hide();
	}
	
	public boolean processResponse(Response response)
	{
		String err = response.getHeader("EXECUTEFAILED");
		//MessageConsole.setText("远端回应：" + ( (err == null) ? "null " : "not null" + err ) );
		if ( (null == err) || ("".equals( err )) )
		{
			String resule = XMLDataParse.getElementText(response.getText(), "ERROR");
			if( "".equals(resule) )
			{
				hide();
				return true;
			}
			else
				setMessage(resule);
		}
		else
			setMessage(err);

		submit.setEnabled(true);
		return false;
	}

	public void center() {
		super.center();
		RootPanel root = RootPanel.get("root");
	    int left = (root.getOffsetWidth() - getOffsetWidth()) >> 1;
	    int top = (root.getOffsetHeight() - getOffsetHeight()) >> 1;
	    setPopupPosition(Math.max(root.getAbsoluteLeft() + left, 0), Math.max(
	        root.getAbsoluteTop() + top, 0));
	}
}
