/**
 * 
 */
package risetek.client.dialog;

import risetek.client.control.RadiusUserStatusController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author wangx
 *
 */
public class UserDateilsInfoDialog extends Composite {

	interface ProudceUiBinder extends UiBinder<Widget, UserDateilsInfoDialog> {}

	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	public interface UserStatusDialogStyle extends CssResource {
	    String StatusOK();
	    String StatusWarning();
	    String StatusNormal();
	    String light();
	    String head();
	}
	
	public  RadiusUserStatusController control;
	
	@UiField 
	public static UserStatusDialogStyle style;
	
	private final static int colCount = 2;
	private Object data[][];
	private final static String[] item = {"序号", "终端号", "用户名称", "分配地址", "备注"};

    @UiField
    public Grid grid;
    
    @UiFactory
    public Grid initGrid() {
    	return new GellMouseEventGrid(item.length, colCount);
    }
	
	@UiField
	Button button;
	
	public UserDateilsInfoDialog(RadiusUserStatusController radiusUserStatusController) {
		Widget w = uiBinder.createAndBindUi(this);
		w.setSize("350px", "200px");
		initWidget(w);

		this.control = radiusUserStatusController;
		grid.getColumnFormatter().setWidth(0, "40%");
		grid.getColumnFormatter().setWidth(1, "60%");
		button.setText("关闭窗口");
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e) {
		Widget parent = this.getParent().getParent();
		if(parent instanceof DialogBox){
			DialogBox dialog = (DialogBox)parent;
			dialog.hide();
		}
	}
	
	public void render(String[] data)
	{
		grid.setText(0, 1, data[0]);
		grid.setText(1, 1, data[2]);
		grid.setText(2, 1, data[3]);
		grid.setText(3, 1, data[5]);
		if(data[6].length()<=1){
			grid.setText(4, 1, " ");
		} else {
			grid.setText(4, 1, data[6]);
		}
		for(int i=0;i<item.length;i++){
    		grid.setText(i, 0, item[i]);
    		grid.getCellFormatter().setStyleName(i, 0, style.head());
//    		if(i+1 == data.length){
//    			if("1".equalsIgnoreCase(data[1])){
//    				grid.setText(i, 1, "已通过认证");
//    				grid.getCellFormatter().setStyleName(i, 1, style.StatusNormal());
//    			} else if ("2".equalsIgnoreCase(data[1])){
//    				grid.setText(i, 1, "关键设备已通过认证，但连接异常");
//    				grid.getCellFormatter().setStyleName(i, 1, style.StatusWarning());
//    			} else {
//    				grid.setText(i, 1, "已通过认证，并在线");
//    				grid.getCellFormatter().setStyleName(i, 1, style.StatusOK());
//    			}
//    			continue;
//    		}
//    		if(i != 0){
//    			grid.setText(i, 1, data[i + 1]);
//    		} else {
//    			grid.setText(i, 1, data[0]);
//    		}
    	}
	}

	class GellMouseEventGrid extends Grid {
    	
    	GellMouseEventGrid(int rows, int columns) {
    		super(rows, columns);
    		setCellPadding(2);
    		setCellSpacing(0);

    		// 增加鼠标事件。
    		sinkEvents(Event.MOUSEEVENTS);
    	}
    	
    	public void onBrowserEvent(Event event) {
    		Element td = DOM.eventGetTarget(event);       
	        Element tr = DOM.getParent(td);
	        Element body = DOM.getParent(tr);
	        int row = DOM.getChildIndex(body, tr);
	        String index = getText(row, 0);

	        try{
		        int id = Integer.parseInt(index);
		        if( id <= 0){
		        	return;
		        }
	        }catch( NumberFormatException nfe){
	        	return;
	        }
	        
	        switch (DOM.eventGetType(event))
	        {
	        case Event.ONMOUSEOVER:
	            onMouseOver(td, row);
	            break;
	        case Event.ONMOUSEOUT:
	       	// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
	        case Event.ONCLICK:
	        	onMouseOut(td, row, index);
	        	break;
	        }
	        super.onBrowserEvent(event);
    	}
    	
		public void onMouseOut(Element td, int row, String index) {
			if(row>=data.length){
				for(int i=0;i<colCount;i++){
					grid.getCellFormatter().setStyleName(row, i, style.StatusNormal());
				}
				return;
			}
			String flag = null;
			for(int i=0;i<data.length;i++){
				if(data[i][0].equals(index)){
					flag = (String) data[i][6];
				}
			}
			if(flag.equals("1")){
				for(int i=0;i<colCount;i++){
					grid.getCellFormatter().setStyleName(row, i, style.StatusOK());
				}
			} else {
				for(int i=0;i<colCount;i++){
					grid.getCellFormatter().setStyleName(row, i, style.StatusNormal());
				}
			}
		}
		
		public void onMouseOver(Element td, int row) {
			for(int i=0;i<colCount;i++){
				grid.getCellFormatter().setStyleName(row, i, style.light());
			}
//			getRowFormatter().setStyleName(row, "light");
		}
    }
}
