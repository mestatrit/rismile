package risetek.client.dialog;

import java.util.List;

import risetek.client.model.IfModel;
import risetek.client.view.InterfaceView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.view.IView;

public class IfEditDialog  extends CustomDialog {
	public static final int ADD = 0;
	public static final int MODIFY = 1;
	private IfInputPanel panel;

	InterfaceView parent;
	IfModel ifModel;
	Widget widget;
	private int code = ADD; 
	
	public IfEditDialog(IView parent){
		super(parent);
		
		addStyleName("dialog");
		setSize("600","200");
		confirm.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				boolean result = panel.checkAll();
				if(result){
					String infCmd = panel.getInfCmd(); 
					String commands = panel.getCommands();
					String text = "interface=" + infCmd + "&commands=" + commands;
					//parent.ifController.setDialer(new DialogAction(IfEditDialog.this, parent), text);
				}else {
					Window.alert("输入有错误，请修改！");
				}
			}
			
		});
		
	}
	public IfEditDialog(final InterfaceView parent, final Widget widget, final List<String> dialerNameList){
		this(parent);
		this.parent = parent;
		this.widget = widget;
		panel = new IfInputPanel(dialerNameList);
		add(panel,DockPanel.CENTER);
		
	}
	
	public IfEditDialog(final InterfaceView parent, final IfModel ifModel, final Widget widget){
		this(parent);

		this.parent = parent;
		this.ifModel = ifModel;
		this.widget = widget;
		panel = new IfInputPanel(ifModel);
		add(panel,DockPanel.CENTER);
	}
	public void show(){
		super.show();
		center();
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getOffsetHeight();
	}
	public void setFirstFocus() {
		// TODO Auto-generated method stub
		
	}


}
