package com.risetek.rismile.client.dialog;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.XMLDataParse;

public abstract class CustomDialog extends MyDialog {
	private final DockPanel panel = new DockPanel();
	public final Button confirm = new Button("确定");
	protected final Button cancel = new Button("取消");
	private final Label note = new Label();
	private final Label message = new Label();

	Composite parent;

	public CustomDialog(Composite parent) {
		super(false,true);
		setStyleName("rismile-dialog");
		this.parent = parent;
		panel.setWidth("100%");
		panel.add(note, DockPanel.NORTH);
		
		final DockPanel toolPanel = new DockPanel();
		toolPanel.setWidth("100%");
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		toolPanel.add(confirm, DockPanel.EAST);
		confirm.setStyleName("rismile-dialog-button");
		
		toolPanel.add(cancel, DockPanel.WEST);
		cancel.setStyleName("rismile-dialog-button");
		cancel.addClickListener(this);
		
		panel.add(toolPanel, DockPanel.SOUTH);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(message, DockPanel.SOUTH);
		message.setStyleName("rismile-dialog-label");
		panel.setSpacing(10);
		
		cancel.setTabIndex(101);
		confirm.setTabIndex(102);
		//focusPanel.setTabIndex(105);
		
		setWidget(panel);
		
		//close.addStyleName("close-button");
		//close.addClickListener(this);
		//close.setTabIndex(103);
	}
	public void setNote(String note){
		this.note.setText(note);
	}
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	public void show(String tips){
		setText(tips);
		mask();
		super.show();
		center();
	}
	
	public void show(){
		mask();
		super.show();
		center();
	}
	
	public void add(Widget widget, DockLayoutConstant direction){
		panel.add(widget,direction);
	}
	
	
	// 这里把 开始和结束两个元素的TAB跳格做限制，防止焦点转移到别的非Dialog元素上。
	public boolean onEventPreview(Event event) {
	    Element target = DOM.eventGetTarget(event);
	    
	    int type = DOM.eventGetType(event);
	    
	    if( type == Event.ONKEYDOWN && DOM.eventGetKeyCode(event) == KeyboardListener.KEY_TAB 
	    		&& !DOM.eventGetShiftKey(event)){
	    	if(target.equals(close.getElement())){
	    		return false;
	    	}
	    }
	    else if( type == Event.ONKEYDOWN && DOM.eventGetKeyCode(event) == KeyboardListener.KEY_TAB 
	    		&& DOM.eventGetShiftKey(event)){
	    	if(target.equals(getFirstTabIndex().getElement())){
	    		return false;
	    	}
	    }									
	    return super.onEventPreview(event);
	}
	
	abstract public Widget getFirstTabIndex();

	/*
	 * 将背景单元（parent ）设定为半透明状态。
	 */
	public void mask()
	{
		mask.setPropertyString("className", "rismile-mask");
		
		DOM.setIntStyleAttribute(mask, "height", parent.getOffsetHeight());
		parent.getElement().appendChild(mask);
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
				setMessage("远端执行正常");
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
			setMessage("远端执行错误！");
			return false;
		}
	}
}
