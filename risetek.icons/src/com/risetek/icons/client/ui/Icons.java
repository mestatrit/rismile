package com.risetek.icons.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class Icons extends Composite {

	private final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
	private final FileUpload fileUpload = new FileUpload();
	private Button upload = new Button("开始上传文件");
	private final FormPanel formPanel = new FormPanel();
	private final VerticalPanel vpOuter = new VerticalPanel();
	private final VerticalPanel vp = new VerticalPanel();
	private final HTML status = new HTML(" ");

	public Icons() {
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.addNorth(new HTML("WHAT IS THIS"), 20);
		
		fileUpload.setName("file1");
		//upload.setWidth("210px");
		//upload.setHeight("40px");
		upload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (fileUpload.getFilename().length() != 0) {
					formPanel.setAction("/icons/"+fileUpload.getFilename());
					formPanel.submit();
				} else
					Window.alert("请选择上传的文件!");
			}
		});
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);

		formPanel.add(fileUpload);
		formPanel.addSubmitHandler(new SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				status.setHTML("开始上传...");
			}
		});

		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				status.setHTML(event.getResults());
			}
		});
		vp.setBorderWidth(1);
		vp.add(formPanel);
		vp.add(upload);
		vp.add(status);
		// vp.setSize("80%", "100%");
		vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		vpOuter.setBorderWidth(1);
		vpOuter.setSize("100%", "100%");
		vpOuter.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpOuter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpOuter.add(vp);
		outer.add(vpOuter);

		initWidget(outer);
	}

}
