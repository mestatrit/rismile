package risetek.client.view;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BundView {
	VerticalPanel panel = new VerticalPanel();

	final CheckBox mppcRadioButton = new CheckBox();
	
	public BundView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");

		final Label compressLabel = new Label("COMPRESS");
		
		panel.add(compressLabel);
		
		final HorizontalPanel compressPanel = new HorizontalPanel();
		panel.add(compressPanel);

		//compressPanel.add(nocompressRadioButton);
		//nocompressRadioButton.setText("no-compress");

		compressPanel.add(mppcRadioButton);
		mppcRadioButton.setText("mppc");
		
		outerPanel.add(panel);
	}
	
}
