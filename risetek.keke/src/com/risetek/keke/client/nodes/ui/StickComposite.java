package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.resources.IconManage;
import com.risetek.keke.client.ui.UiKeke;

public class StickComposite extends Composite {
	DockPanel outPanel = new DockPanel();
	Image img = new Image();

	public StickComposite(Node node) {
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		
		// ICON 的处理。 
		img.setResource(IconManage.getIcon(node.imgName));
		outPanel.add(img, DockPanel.WEST);
		outPanel.setCellWidth(img, "80px");
		outPanel.setCellHorizontalAlignment(img, HasHorizontalAlignment.ALIGN_CENTER);
		outPanel.setCellVerticalAlignment(img, HasVerticalAlignment.ALIGN_MIDDLE);

		initWidget(outPanel);
		setStyleName("KekeComposite");
	}
}
