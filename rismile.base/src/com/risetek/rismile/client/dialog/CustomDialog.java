package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.risetek.rismile.client.view.IView;

public abstract class CustomDialog extends MyDialog {
	private final DockPanel panel = new DockPanel();
	public final Button confirm = new Button("确定");
	protected final Button cancel = new Button("取消");
	private final Label note = new Label();
	private final Label message = new Label();
	//private  Element mask;
	IView parent;
	//private final Button close  = new Button(" ");
	public CustomDialog(IView parent) {
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
	
	public void enableConfirm(boolean enabled){
		confirm.setEnabled(enabled);
	}
	public void setText(String text){
		
		/*DockPanel head = new DockPanel();
		
		head.setWidth("100%");
		
		//head.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		//head.add(close, DockPanel.EAST);
		head.setCellHorizontalAlignment(close, HasHorizontalAlignment.ALIGN_RIGHT);
		
		HTML title = new HTML(text);
		head.add(title, DockPanel.WEST);
		head.setCellVerticalAlignment(title, HasVerticalAlignment.ALIGN_MIDDLE);
		SimplePanel dummyContainer = new SimplePanel();
		dummyContainer.add(head);

		String innerHtml = DOM.getInnerHTML(dummyContainer.getElement());
		this.setHTML(innerHtml);*/
		super.setText(text);
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
		parent.mask();
	}
	public void show(){
		super.show();
		center();
		parent.mask();
	}
	public void onClick(Widget sender) {
		parent.unmask();
    	this.hide();
    	
	}

	public void add(Widget widget, DockLayoutConstant direction){
		panel.add(widget,direction);
	}
	public boolean onEventPreview(Event event) {
	    Element target = DOM.eventGetTarget(event);
	    
	    int type = DOM.eventGetType(event);
	    if( type == Event.ONKEYDOWN && DOM.eventGetKeyCode(event) == KeyboardListener.KEY_TAB 
	    		&& !DOM.eventGetShiftKey(event)){
	    	if(target.equals(close.getElement())){
	    		return false;
	    	}
	    }else if( type == Event.ONKEYDOWN && DOM.eventGetKeyCode(event) == KeyboardListener.KEY_TAB 
	    		&& DOM.eventGetShiftKey(event)){
	    	if(target.equals(getFirstTabIndex().getElement())){
	    		return false;
	    	}
	    }									
	    return super.onEventPreview(event);
	}
	
	abstract public void setFirstFocus();
//	abstract public int getParentHeihgt();
	abstract public Widget getFirstTabIndex();
}
