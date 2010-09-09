package com.risetek.icons.client.ui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Icons extends Composite {
	private final VerticalPanel vpOuter = new VerticalPanel();

	public Icons() {
		//setSize("100%", "100%");
		UploadComposite upload1 = new UploadComposite("upload1");
		UploadComposite upload2 = new UploadComposite("upload2");
		UploadComposite upload3 = new UploadComposite("upload3");
		UploadComposite upload4 = new UploadComposite("upload4");

		vpOuter.setBorderWidth(1);
		vpOuter.setSize("100%", "100%");
		vpOuter.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpOuter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpOuter.add(upload1);
		vpOuter.add(upload2);
		vpOuter.add(upload3);
		vpOuter.add(upload4);

		initWidget(vpOuter);
	}

	private class UploadComposite extends Composite {
		private final FileUpload fileUpload = new FileUpload();
		private final FormPanel formPanel = new FormPanel();
		private final VerticalPanel vp = new VerticalPanel();
		private final HTML status = new HTML("文件传输状态");

		public UploadComposite(String name) {
			fileUpload.setName("name");

			fileUpload.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {

					if (fileUpload.getFilename().length() != 0) {
						formPanel.setAction("/icons/"
								+ fileUpload.getFilename());
						formPanel.submit();
					} else {
						status.setTitle("未选择上传的文件!");
					}
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
			vp.add(status);
			// vp.setSize("80%", "100%");
			vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			initWidget(vp);
		}
	}
}
