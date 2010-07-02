package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.context.UiKeke;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.resources.IconManage;

public class PromotionComposite extends Composite {
	
	Label Type = new Label();
	public Label brief = new Label();
	Image img = new Image();
	DockPanel outPanel = new DockPanel();
	DockPanel tipsPanel = new DockPanel();
	
	
	public PromotionComposite(Node node) {
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		Type.setText(node.Ticker);
		brief.setText(node.Promotion);
		img.setResource(IconManage.getIcon(node.imgName));
		outPanel.add(img, DockPanel.WEST);

		outPanel.setCellWidth(img, "80px");
		outPanel.setCellHorizontalAlignment(img, HasHorizontalAlignment.ALIGN_CENTER);
		outPanel.setCellVerticalAlignment(img, HasVerticalAlignment.ALIGN_MIDDLE);

		tipsPanel.setSize("100%", "100%");
		tipsPanel.add(Type, DockPanel.NORTH);
		tipsPanel.add(brief, DockPanel.CENTER);
		outPanel.add(tipsPanel, DockPanel.EAST);
		initWidget(outPanel);
		setStyleName("KekeComposite");
	}

}
