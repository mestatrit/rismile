package com.risetek.icons.client.ui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
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
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class TreeUpIcons extends Composite {
	private final VerticalPanel vpOuter = new VerticalPanel();
	public TreeUpIcons() {
		vpOuter.setBorderWidth(1);
		vpOuter.setSize("100%", "100%");
		vpOuter.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpOuter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(vpOuter);
	}



	@Override
	protected void onLoad() {
		// TODO: FIXME: 清空 vpOuter.
		try {
			RequestBuilder rqBuilder = new RequestBuilder(RequestBuilder.GET, "/nulllist/");

			rqBuilder.sendRequest(null, new RequestCallback(){
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("列取文件错误");
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					String xml = response.getText();
					if( response.getStatusCode() != 200 ) {
						Window.alert(xml);
						return;
					}
					Document doc = XMLParser.parse(xml);
					NodeList list = doc.getElementsByTagName("icon");
					for(int loop=0; loop < list.getLength() && loop < 20; loop++) {
						Node node = list.item(loop);
						String name = getStickAttribute(node,"name");
						vpOuter.add(new UploadComposite(name));
					}
					
					
				}});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	private static String getStickAttribute(Node node, String attribute) {
		Node n = node.getAttributes().getNamedItem(attribute);
		if( n != null )
			return n.getNodeValue();
		return null;
	}
	
	
	
	private class UploadComposite extends Composite {
		private final FileUpload fileUpload = new FileUpload();
		private final FormPanel formPanel = new FormPanel();
		private final VerticalPanel vp = new VerticalPanel();
		private final HTML status = new HTML("文件传输状态");
		private final HTML missfile = new HTML();

		public UploadComposite(final String name) {
			missfile.setText(name);
			fileUpload.setName(name);
			fileUpload.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {

					if (fileUpload.getFilename().length() != 0) {
						formPanel.setAction("/nulllist/" + name);
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
			vp.add(missfile);
			vp.add(status);
			vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			initWidget(vp);
		}
	}
}
