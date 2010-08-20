package com.risetek.rismile.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.XMLDataParse;

public class CustomDialog extends DialogBox {

	private static CustomDialogUiBinder uiBinder = GWT
			.create(CustomDialogUiBinder.class);

	interface CustomDialogUiBinder extends UiBinder<Widget, CustomDialog> {}

	@UiField protected Label label;

	@UiField protected VerticalPanel mainPanel;
	
	@UiField protected Label message;
	
	@UiField public Button submit;
	
	@UiField Button cancel;

	private final SimplePanel outePanel = new SimplePanel();

	public CustomDialog() {
		outePanel.add(uiBinder.createAndBindUi(this));
		
		setWidget(outePanel);
		setGlassEnabled(true);
		//setAnimationEnabled(true);
		submit.setTabIndex(1001);
		cancel.setTabIndex(1002);
	}
	
	@Override
	public void setText(String text) {
		String title = "<TABLE width='100%'><TR><TD ALIGN='LEFT' style='font-weight: bold;'>"+text+"</TD><TD ALIGN='RIGHT'>Esc关闭</TD></TR></TABLE>";
		setHTML(title);
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

	@Override
	public void show() {
		super.show();
		center();
	}

	public void center() {
		// TODO:
		// FIXME:
		// 不能按照预想的居中。
		
		RootPanel root = RootPanel.get("root");
/*
		RootPanel root = RootPanel.get("diagto");
		if( null == root )			root = RootPanel.get("root");
		*/
/*
		RootLayoutPanel root = RootLayoutPanel.get();
*/		
	    int left = (root.getOffsetWidth() - this.getOffsetWidth()) >> 1;
	    int top = (root.getOffsetHeight() - this.getOffsetHeight()) >> 1;
	    setPopupPosition(Math.max(root.getAbsoluteLeft() + left, 0), Math.max(
	        root.getAbsoluteTop() + top, 0));
	}

	@Override
	protected void continueDragging(MouseMoveEvent event) {
		// 阻止拖动动作。
		super.continueDragging(event);
	}

	@Override
	public boolean onKeyDownPreview(char key, int modifiers) {
		int code = (int)key;
		if(code==KEY.ESC){
			hide();
		}
		// 任何地点按下enter都表示确定？
		/*
		if(code==KEY.ENTER){
			submit.click();
		}
		*/
		return true;
	}
}
