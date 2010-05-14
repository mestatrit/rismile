package com.risetek.rismile.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class UpfileDialog extends CustomDialog {

	private final FormPanel formPanel = new FormPanel();
	
	private final FileUpload fileUpload = new FileUpload();

	private final HTML status = new HTML(" ");
	
	private Button upload = new Button("开始上传文件");
	
	public UpfileDialog()
	{
		fileUpload.setName("file1");

		setText("升级程序");
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		Grid gridFrame = new Grid(1, 2);
		gridFrame.setWidget(0, 0, new Label("程序：", false));
		gridFrame.setWidget(0, 1, fileUpload);
		
		submit.setVisible(false);
		
//		gridFrame.setWidget(2, 1, upload);
		upload.setTabIndex(1);
		upload.setWidth("210px");
		upload.setHeight("40px");
		upload.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if( fileUpload.getFilename().length() != 0 )
					formPanel.submit();
				else
					Window.alert("请选择上传的文件!");
			}
		});
		
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction("forms/uploads");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.add(gridFrame);
		formPanel.addSubmitHandler(new SubmitHandler(){
			public void onSubmit(SubmitEvent event) {
				cancel.setVisible(false);
				status.setHTML("开始上传...");
			}
		}
		);

		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			public void onSubmitComplete(SubmitCompleteEvent event) {
				status.setHTML(event.getResults());
				hide();
			}
		});

		vp.add(formPanel);
		vp.add(upload);
		
		vp.add(new HTML("请谨慎使用该功能!"));
		
		status.addStyleName("info-Label");
		vp.add(status);
		mainPanel.add(vp);
	}
	public Widget getFirstTabIndex() {
		return null;
	}
	
	public boolean isValid() {
		return true;
	}

}
