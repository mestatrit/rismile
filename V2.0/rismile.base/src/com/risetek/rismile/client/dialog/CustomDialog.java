package com.risetek.rismile.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.risetek.rismile.client.utils.XMLDataParse;

public abstract class CustomDialog extends DialogBox  {
	
	interface localUiBinder extends UiBinder<Widget, CustomDialog> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	
	@UiField
	DockPanel panel;

	@UiField
	public Button confirm;

	@UiField
	Button cancel;


	@UiField
	Label message;

	
	public CustomDialog() {
		super(false,true);
		setGlassEnabled(true);

		// 设置到极大，使得能够在mask上显示。
		getElement().getStyle().setZIndex(200003);

//		setAnimationEnabled(true);
		
		Widget w = uiBinder.createAndBindUi(this);
		setWidget(w);
		cancel.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				hide();
			}});
		
	}

	public void setMessage(String message){
		this.message.setText(message);
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
	}
	
	public void show(){
		super.show();
		center();
	}
	
	@Override
	public void center() {
		int left = (Window.getClientWidth() - this.getOffsetWidth()) >> 1;
		int top = (Window.getClientHeight() - this.getOffsetHeight()) >> 1;
		setPopupPosition(left, top);
	}

	public void add(Widget widget, DockLayoutConstant direction){
		panel.add(widget,direction);
	}

	// 这里把 开始和结束两个元素的TAB跳格做限制，防止焦点转移到别的非Dialog元素上。
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		
		EventTarget target = event.getNativeEvent().getEventTarget();
	    
	    int type = event.getTypeInt();
	    
	    if( type == Event.ONKEYDOWN && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB )
	    {
	    	if( (!event.getNativeEvent().getShiftKey() && target.equals(confirm.getElement()) ) ){
	    		event.cancel();
	    	}
	    }
	    super.onPreviewNativeEvent(event);
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
				// setMessage("远端执行正常");
				hide();
				return true;
			}
			else
			{
				confirm.setEnabled(true);
				setMessage(resule);
				return false;
			}
		}
		else
		{
			confirm.setEnabled(true);
			setMessage(err);
			return false;
		}
//		return true;
	}

}
