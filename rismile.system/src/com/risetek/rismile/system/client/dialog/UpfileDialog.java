package com.risetek.rismile.system.client.dialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.system.client.view.SystemView;

public class UpfileDialog extends CustomDialog {
	SystemView parent;
	private final DockPanel panel = new DockPanel();
	
	private final FormPanel formPanel = new FormPanel();
	private final Grid gridFrame = new Grid(3, 2);
	
	private final Label      upLabel    = new Label("文件：", false);
	//private final Label      checkLabel = new Label("上传选项：", false);
	
	private final FileUpload fileUpload = new FileUpload();
	//private final CheckBox   checkBox   = new CheckBox("强制删除已存在的文件");
	private final Button     submit     = new Button("开始上传文件");

	private final HTML html = new HTML();
	
	public UpfileDialog(final SystemView parent){
		
		this.parent = parent;
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		fileUpload.setName("file1");
		//checkBox.setName("force_remove");
		
		gridFrame.setWidget(0, 0, upLabel);
		gridFrame.setWidget(0, 1, fileUpload);
		//gridFrame.setWidget(1, 0, checkLabel);
		//gridFrame.setWidget(1, 1, checkBox);
		gridFrame.setWidget(2, 1, submit);
		
		submit.setWidth("7em");
		submit.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				formPanel.submit();
			}
		
		});
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction("forms/uploads");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.add(gridFrame);
		formPanel.addFormHandler(new FormHandler(){

			public void onSubmit(FormSubmitEvent event) {
				// TODO Auto-generated method stub
				html.setHTML("开始上传...");
			}
			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				html.setHTML(event.getResults());
				unmask();
				hide();
			}
			
		});
		panel.add(formPanel, DockPanel.NORTH);
		
		html.addStyleName("sys-info-Label");
		panel.add(html, DockPanel.SOUTH);
		
		this.cancel.setVisible(false);
		this.confirm.setVisible(false);
		
		add(panel,DockPanel.CENTER);
		setText("上传文件");
		setSize("320","240");
	}
	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub

	}

}
