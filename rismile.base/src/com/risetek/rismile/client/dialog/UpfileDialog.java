package com.risetek.rismile.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class UpfileDialog extends CustomDialog {

	private final DockPanel panel = new DockPanel();
	
	private final FormPanel formPanel = new FormPanel();
	
	private final FileUpload fileUpload = new FileUpload();

	private final HTML html = new HTML();
	
	public UpfileDialog()
	{
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		fileUpload.setName("file1");
		//checkBox.setName("force_remove");
		
		Grid gridFrame = new Grid(3, 2);
		gridFrame.setWidget(0, 0, new Label("程序：", false));
		gridFrame.setWidget(0, 1, fileUpload);
		//gridFrame.setWidget(1, 0, checkLabel);
		//gridFrame.setWidget(1, 1, checkBox);
		
		Button submit = new Button("开始上传文件");
		gridFrame.setWidget(2, 1, submit);
		submit.setWidth("7em");
		submit.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});

		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction("forms/uploads");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.add(gridFrame);
		formPanel.addSubmitHandler(new SubmitHandler(){
			public void onSubmit(SubmitEvent event) {
				html.setHTML("开始上传...");
			}
		}
		);

		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			public void onSubmitComplete(SubmitCompleteEvent event) {
				html.setHTML(event.getResults());
				hide();
			}
		});

		panel.add(formPanel, DockPanel.NORTH);
		panel.add(new HTML("请谨慎使用该功能!"), DockPanel.SOUTH);
		
		html.addStyleName("info-Label");
		panel.add(html, DockPanel.SOUTH);
		
		
		cancel.setVisible(false);
		confirm.setVisible(false);
		
		add(panel,DockPanel.CENTER);
		setText("升级程序");
		setSize("340","130");
	}
	public Widget getFirstTabIndex() {
		return null;
	}

}
