package com.risetek.rismile.client.dialog;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.risetek.rismile.client.utils.XMLDataParse;

public abstract class CustomDialog extends MyDialog implements ClickHandler {
	private final DockPanel panel = new DockPanel();
	public final Button confirm = new Button("确定");
	protected final Button cancel = new Button("取消");
	private final Label note = new Label();
	private final Label message = new Label();
	// 用来实现半透明屏蔽
	Element mask = DOM.createDiv();
	/*
	 * 将自己灰色屏蔽取消。
	 */
	public void unmask() {
		Element m = DOM.getElementById("maskPanel");
		if( m != null )
			m.removeChild(mask);
		//mask.getParentElement().removeChild(mask);
	}

	public CustomDialog() {
		super(false,true);
		
		close.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}});

		mask.setPropertyString("className", "mask");
		
		setStyleName("rismile-dialog");
		panel.setWidth("100%");
		panel.add(note, DockPanel.NORTH);
		
		final DockPanel toolPanel = new DockPanel();
		toolPanel.setWidth("100%");
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		toolPanel.add(confirm, DockPanel.EAST);
		confirm.setStyleName("button");
		
		toolPanel.add(cancel, DockPanel.WEST);
		cancel.setStyleName("button");
		cancel.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				hide();
			}});
		
		panel.add(toolPanel, DockPanel.SOUTH);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(message, DockPanel.SOUTH);
		message.setStyleName("message");
		panel.setSpacing(10);
		
		cancel.setTabIndex(101);
		confirm.setTabIndex(102);
		//focusPanel.setTabIndex(105);
		
		setWidget(panel);
		
	}
	public void setNote(String note){
		this.note.setText(note);
	}
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
		mask();
	}
	
	public void show(){
		super.show();
		center();
		mask();
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
	    	if( (!event.getNativeEvent().getShiftKey() && target.equals(close.getElement()) )
	    		||	(event.getNativeEvent().getShiftKey() && target.equals(getFirstTabIndex().getElement())) ){
	    		event.cancel();
	    	}
	    }
	    super.onPreviewNativeEvent(event);
	}

	abstract public Widget getFirstTabIndex();

	/*
	 * 将背景单元（parent ）设定为半透明状态。
	 */
	public void mask()
	{
		Element m = DOM.getElementById("maskPanel");
		mask.getStyle().setPropertyPx("width", m.getOffsetWidth());
		mask.getStyle().setPropertyPx("height", m.getOffsetHeight());
		if( m != null )
			m.appendChild(mask);
		/*
		Widget w = RootPanel.get("maskPanel");
		mask.getStyle().setPropertyPx("width", w.getOffsetWidth());
		mask.getStyle().setPropertyPx("height", w.getOffsetHeight());
		w.getElement().appendChild(mask);
		*/
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
	}

	public void hide() {
		super.hide();
		unmask();
	}

	@Override
	public void onClick(ClickEvent event) {
		hide();
	}
}
