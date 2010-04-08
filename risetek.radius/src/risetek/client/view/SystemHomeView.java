package risetek.client.view;

import risetek.client.control.ProduceHomeController;
import risetek.client.model.ProduceData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;

public class SystemHomeView extends Composite {
	interface ProudceUiBinder extends UiBinder<Widget, SystemHomeView> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	@UiField SpanElement serial;
	@UiField SpanElement status;
	public ProduceHomeController control;

	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
	}

	public SystemHomeView(ProduceHomeController control) {
		this.control = control;
		Widget w = uiBinder.createAndBindUi(this);
//		w.setHeight(Entry.SinkHeight);  //wangx
		initWidget(w);
	}

	public void render(ProduceData data) {
		serial.setInnerText(data.serial);
		status.setInnerText(data.status);
	}

	public void onLoad() {
		control.load();
	}
}
